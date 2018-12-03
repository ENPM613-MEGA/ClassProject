package controller;


import DAO.DocumentDAO;
import domain.Document;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.JSONHelper;
import utils.POLSHelper;
import utils.Validator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/document")
public class DocumentsController {
    private DocumentDAO documentDAO;
    private Validator validator;

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}

    @Autowired
    public void setDocumentDAO(DocumentDAO documentDAO) {this.documentDAO = documentDAO;}

    /*
    *   upload a file to fileSystem, the request should include [token, file, userId, classId, type, (publish)]
    * */
    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam MultipartFile file, HttpServletRequest request) {

        Map<String, Object> mapModel = new HashMap<>();
        Integer cId = null;
        Integer uId = null;
        String type = request.getParameter("type");
        Boolean publish = true; //default
        String token = request.getParameter("token");

        if (request.getParameter("publish") != null) {
            publish = request.getParameter("publish").toLowerCase().equals("true");
        }
        String errorMessage = "ERROR: ";

        // check input valid
        if (type == null || !validator.isFileTypeValid(type)) {
            return POLSHelper.failureReturnConstructor("type not valid!");
        }
        try {
             cId = Integer.valueOf(request.getParameter("cId"));
             uId = Integer.valueOf(request.getParameter("uId"));
        }catch (Exception e) {
            e.printStackTrace();
            errorMessage += "classId or userId not included !";
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {// check user privilege
            errorMessage += "Invalid upload, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        // upload file
        System.out.println("文件长度: " + file.getSize());
        System.out.println("文件类型: " + file.getContentType());
        System.out.println("文件名称: " + file.getName());
        System.out.println("文件原名: " + file.getOriginalFilename());
        System.out.println("===================");

        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        String destFileName = realPath + "/" + cId + "/" + file.getOriginalFilename();
        File newfile = new File(destFileName);

        if (file.isEmpty()) {
            errorMessage += "File cannot be empty!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (newfile.exists()) { // file name already exists
            errorMessage += "Cannot upload file, the file name already existed!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (destFileName.endsWith(File.separator)) { // detined file name invalid
            errorMessage += "The destined file cannot be folder!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (!newfile.getParentFile().exists()) { // parent dir doesn't exists
            System.out.println("Try to create parent folder...");
            if (!newfile.getParentFile().mkdirs()) {
                System.out.println("Create parent folder fails");
                return POLSHelper.failureReturnConstructor("Cannot find the destined file!");
            }
        }

        if (type.toLowerCase().equals("syllabus") && !validator.isSyllabusUnique(cId)) {
            //TODO: handle the unique syllabus problem
        }

        try{
            //insert to db
            Document.DocumentBuilder documentBuilder = new Document.DocumentBuilder(cId, file.getOriginalFilename(), type);
            documentBuilder.setPath(destFileName);
            documentBuilder.setCreateDate(new Date());
            documentBuilder.setPublish(publish);
            documentDAO.createNewDocument(documentBuilder.build());

            FileUtils.copyInputStreamToFile(file.getInputStream(), newfile);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
    return mapModel;
    }

    /*
    *  upload a video, the request should include [userId, classId, filename, url, token]
    * */
    @RequestMapping(value = "/upload-video", method = RequestMethod.POST)
    public Map<String, Object> uploadVideo(HttpServletRequest request) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(request);
        Integer cId = input.getInt("cId");
        Integer uId = input.getInt("uId");
        String type = "video"; // default
        String videoName = input.getString("videoname");
        String token = input.getString("token");
        String path = input.getString("url");
        String errorMessage = "ERROR: ";

        if (path == null || videoName == null) {
            return POLSHelper.failureReturnConstructor("url and video name are required!");
        }


        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {// check user privilege
            errorMessage += "Invalid upload, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        try {
            Document.DocumentBuilder documentBuilder = new Document.DocumentBuilder(cId, videoName, type);
            documentBuilder.setPath(path);
            documentBuilder.setCreateDate(new Date());
            documentDAO.createNewDocument(documentBuilder.build());
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * delete a file, [fileId, userId, token] are required
    *
    * */
    @RequestMapping(value = "/delete-file/{fId}&{uId}&{token}", method = RequestMethod.POST)
    public Map<String, Object> deleteFile(@PathVariable int fId, @PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        Document document = documentDAO.getDocumentByFID(fId);

        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, document.getClassId())) {// check user privilege
            return POLSHelper.failureReturnConstructor("user do not have the privilige to upload this file!");
        }

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!document.getType().equals("video")) { // check if not video, delete the file in the local space
            File delFile = new File(document.getPath());
            if (!delFile.delete()) {
                System.out.println("delete file" + delFile.getName() + "Failed!");
                return POLSHelper.failureReturnConstructor("delete file failed!");
            }
        }

        try {
            documentDAO.deleteFileByID(fId);
            mapModel.put("status", "success");
        }catch (Exception e) {
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

     /*
     * download a file from server, requires [token, fileId, userId]
     *
     * */
    @RequestMapping(value = "/download-file/{fId}&{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> downloadFile(@PathVariable int fId, @PathVariable int uId, @PathVariable String token,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> mapModel = new HashMap<>();
        ServletContext context = request.getServletContext();
        Document document = documentDAO.getDocumentByFID(fId);

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            throw new RuntimeException("token not match to user!");
        }

        if (!validator.isMemberOfClass(uId, document.getClassId())) {//check privilige
            throw new RuntimeException("user do not have the access to the source!");
        }

        if (document.getType().toLowerCase().equals("video")) {
            mapModel.put("status", "success");
            mapModel.put("document", document);
            return mapModel;
        }

        String fullPath = document.getPath();
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
        String mimeType = context.getMimeType(fullPath); // get the MIME Type based on file path

        //check mineType
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int)downloadFile.length());
        //set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // download the file
        InputStream in = new FileInputStream(downloadFile);
        FileCopyUtils.copy(in, response.getOutputStream());
        return null;
    }


    /*
    * edit the visibility (publish attribute) of document
    *
    * */
    @RequestMapping(value = "/update-document", method = RequestMethod.POST)
    public Map<String, Object> updateFile(HttpServletRequest request) {

        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(request);
        int id = input.getInt("id");
        int fId = input.getInt("fId");
        String token = input.getString("token");
        boolean newPublishStatus = input.getBoolean("publish");
        Document document = documentDAO.getDocumentByFID(fId);

        if (token == null || !validator.isTokenValid(id, token)) {
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isIntructor(id) || !validator.isMemberOfClass(id, document.getClassId())) {//check privilige
            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
        }

        try{
            documentDAO.updateDocument(fId, newPublishStatus);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }


//    /*
//    * get file list of a class
//    * */
//    @RequestMapping(value = "/get-class-files/{cId}&{uId}&{token}", method = RequestMethod.GET)
//    public Map<String, Object> getClassFiles(@PathVariable int cId, @PathVariable int uId, @PathVariable String token) {
//
//        if (token == null || !validator.isTokenValid(uId, token)) {
//            return POLSHelper.failureReturnConstructor("token not match to user!");
//        }
//
//        if (!validator.isMemberOfClass(uId, cId)) {//check privilige
//            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
//        }
//
//        Map<String, Object> mapModel = new HashMap<>();
//        try{
//            mapModel.put("status", "success");
//            mapModel.put("files", documentDAO.getClassFiles(cId));
//        }catch (Exception e) {
//            e.printStackTrace();
//            POLSHelper.failureReturnConstructor(e.getMessage());
//        }
//        return mapModel;
//    }
}
