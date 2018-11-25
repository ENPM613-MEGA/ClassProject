package controller;

import DAO.AccountDAO;
import DAO.DocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.POLSHelper;
import utils.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/class")
public class ClassController {
    private Validator validator;
    private DocumentDAO documentDAO;
    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO){this.accountDAO = accountDAO;}

    @Autowired
    public void setDocumentDAO(DocumentDAO documentDAO){this.documentDAO = documentDAO;}

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}


    /*
     * get file list of a class
     * */
    @RequestMapping(value = "/get-class-files/{cId}&{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassFilesList(@PathVariable int cId, @PathVariable int uId, @PathVariable String token) {

        if (token == null || !validator.isTokenValid(uId, token)) {
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isMemberOfClass(uId, cId)) {//check privilige
            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
        }

        Map<String, Object> mapModel = new HashMap<>();
        try{
            mapModel.put("files", documentDAO.getClassFileList(cId));
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * get student list of a class
    * */
    @RequestMapping(value = "/get-class-students/{cId}&{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassStudentsList(@PathVariable int cId, @PathVariable int uId, @PathVariable String token) {

        if (token == null || !validator.isTokenValid(uId, token)) {
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isMemberOfClass(uId, cId)) {//check privilige
            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
        }

        Map<String, Object> mapModel = new HashMap<>();

        List<Map<String, Object>> studentList = null;
        try{
            mapModel.put("studentList", accountDAO.getClassStudentList(cId));
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }
}
