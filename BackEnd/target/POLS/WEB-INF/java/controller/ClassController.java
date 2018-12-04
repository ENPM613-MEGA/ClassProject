package controller;

import DAO.*;
import domain.Assignment;
import domain.POLSClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private AssignmentDAO assignmentDAO;
    private GradeDAO gradeDAO;
    private POLSClassDAO polsClassDAO;

    @Autowired
    public void setPolsClassDAO(POLSClassDAO polsClassDAO){this.polsClassDAO = polsClassDAO;}

    @Autowired
    public void setGradeDAO(GradeDAO gradeDAO){this.gradeDAO = gradeDAO;}

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO){this.accountDAO = accountDAO;}

    @Autowired
    public void setDocumentDAO(DocumentDAO documentDAO){this.documentDAO = documentDAO;}

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}


    /*
     * get file list of a class
     * */
    @CrossOrigin
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
    @CrossOrigin
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

    /*
     * get all students grades of a class
     * */
    @CrossOrigin
    @RequestMapping(value = "/get-class-grades/{uId}&{cId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassGrades(@PathVariable int uId, @PathVariable int cId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();
        String errorMessage = "ERROR: ";

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {// check user privilege
            errorMessage += "Invalid get, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        Map<String, Object> assResults = new HashMap<>();
        mapModel.put("results", assResults);

        List<Assignment> assOfClass = null;
        try {
            assOfClass = assignmentDAO.getAssListByClass(cId);
        }catch (Exception e) {

        }

        for (Assignment ass : assOfClass) {
            try{
                List<Map<String, Object>> studentsGrades = gradeDAO.getClassStudentsGrades(ass.getId());
                assResults.put(ass.getName(), studentsGrades);
            }catch (Exception e) {
                e.printStackTrace();
                return POLSHelper.failureReturnConstructor(e.getMessage());
            }
        }
        mapModel.put("status", "success");
        return mapModel;
    }

    /*
     * Get assignments of a class
     * */
    @CrossOrigin
    @RequestMapping(value = "/get-class-assignments-list/{uId}&{cId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassAssignmentsList(@PathVariable int uId, @PathVariable int cId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isMemberOfClass(uId, cId)) {//check privilige
            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
        }

        boolean isInstructor = validator.isIntructor(uId);
        try {
            List<Map<String, Object>> assList = assignmentDAO.getClassAssignmentsList(cId);
            if (!isInstructor) {
                for (int i = 0; i < assList.size(); i++) {
                    if (!(Boolean) assList.get(i).get("publish")) {
                        assList.remove(i);
                    }
                }
            }
            mapModel.put("assignmentList", assList);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }


    /*
    * Get the class that the user enrolled in (instructor / student)
    * */
    @CrossOrigin
    @RequestMapping(value = "/get-class-list/{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassListOfUser(@PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }


        try{
            List<Map<String, Object>> classList = polsClassDAO.getClassList(uId);
            mapModel.put("classList", classList);
            mapModel.put("status", "success");
        }catch (Exception e){
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * Get a class by classId
    * */
    @CrossOrigin
    @RequestMapping(value = "/get-class/{uId}&{cId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getClassById(@PathVariable int uId, @PathVariable int cId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isMemberOfClass(uId, cId)) {//check privilige
            return POLSHelper.failureReturnConstructor("user do not have the privilige to update this file!");
        }

        try {
            POLSClass polsClass = polsClassDAO.getClassById(cId);
            mapModel.put("class", polsClass);
            mapModel.put("status", "success");
        }catch (Exception e){
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

}
