package controller;


import DAO.DocumentDAO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.POLSHelper;
import utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
*   upload a file to fileSystem, the request should include the file, userId, and classId
* */
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam MultipartFile file, HttpServletRequest request) {

        Map<String, Object> mapModel = new HashMap<>();
        Integer cId = null;
        Integer uId = null;
        String errorMessage = "ERROR: ";

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

        try{
            FileUtils.copyInputStreamToFile(file.getInputStream(), newfile);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
    return mapModel;
    }


    
}
