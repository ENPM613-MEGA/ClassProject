package controller;


import DAO.DocumentDAO;
import domain.Document;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.JSONHelper;
import utils.POLSHelper;
import utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
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
    *   upload a file to fileSystem, the request should include [file, userId, classId, type, (publish)]
    * */
    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam MultipartFile file, HttpServletRequest request) {

        Map<String, Object> mapModel = new HashMap<>();
        Integer cId = null;
        Integer uId = null;
        String type = request.getParameter("type");
        Boolean publish = true; //default
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

        // check user privilege
        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {
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
    *  upload a video, the request should include [userId, classId, filename, url]
    * */
    @RequestMapping(value = "/upload-video", method = RequestMethod.POST)
    public Map<String, Object> uploadVideo(HttpServletRequest request) {
        Map<String, Object> mapModel = new HashMap<>();
        Integer cId = null;
        Integer uId = null;
        String type = "video"; // static
        String videoName = request.getParameter("videoname");
        String path = request.getParameter("url");
        String errorMessage = "ERROR: ";

        if (path == null || videoName == null) {
            return POLSHelper.failureReturnConstructor("url and video name are required!");
        }

        // check input valid
        try {
            cId = Integer.valueOf(request.getParameter("cId"));
            uId = Integer.valueOf(request.getParameter("uId"));
        }catch (Exception e) {
            e.printStackTrace();
            errorMessage += "classId or userId not included !";
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        // check user privilege
        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {
            errorMessage += "Invalid upload, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
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
}
