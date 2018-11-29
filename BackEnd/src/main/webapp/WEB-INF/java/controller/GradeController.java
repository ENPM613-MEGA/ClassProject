package controller;


import DAO.AccountDAO;
import DAO.AssignmentDAO;
import DAO.GradeDAO;
import domain.Account;
import domain.Grade;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.JSONHelper;
import utils.POLSHelper;
import utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GradeController receives quest from dispatcher-servlet, then accesses data from
 * database and put the information into model and return this model to view. It
 * is a controller that deals with all the quests regarding with grades.
 *
 * Students can see grade for certain assignment.
 * Instructor can see grade for certain assignment and certain student.
 * Instructor can give grade for certain assignment and certain student.
 * Instructor can update grade for certain assignment and certain student.
 *
 * @author --- Jiwen Zhang ----
 */

@RestController
@RequestMapping("/grade")
public class GradeController {
    private GradeDAO gradeDAO;
    private Validator validator;
    private AssignmentDAO assignmentDAO;
    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO){this.accountDAO = accountDAO;}

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}

    @Autowired
    public void setValidator(Validator validator){this.validator = validator;}

    @Autowired
    public void setGradeDAO(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    /**
     * Query for the grade for certain user and certain assignment.
     *
     * @param
     * @return mapModel.
     */
    @RequestMapping(value = "/get-grade/{uId}&{assId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getGrade(@PathVariable int uId, @PathVariable int assId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        try {
            Grade grade = gradeDAO.getGrade(uId, assId);
            mapModel.put("status", "success");
            mapModel.put("grade", grade.getGrade());
        } catch (Exception e) {
            e.printStackTrace();
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /**
     * Update the grade for certain user and certain assignment.
     *
     * @param
     * @return mapModel.
     */
    @RequestMapping(value = "/update-grade", method = RequestMethod.POST)
    public Map<String, Object> updateGrade(HttpServletRequest rq) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(rq);
        int uId = input.getInt("uId");
        int assId = input.getInt("assId");
        String token = input.getString("token");

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        try{
            gradeDAO.updateGrade(uId, assId, input.getInt("grade"));
            mapModel.put("status", "success");
        } catch (Exception e){
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * get grades of a student for a specific class
    * */
    @RequestMapping(value = "/get-student-grades/{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getStudentGrades(@PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if (token == null || !validator.isTokenValid(uId, token)) {// check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (validator.isIntructor(uId)) {
            return POLSHelper.failureReturnConstructor("instructor do not have grades book!");
        }

        try {
            Account student = accountDAO.getAccountById(uId);
            List<Map<String, Object>> gradesBook = gradeDAO.getStudentGrades(uId);
            mapModel.put("gradesBook", gradesBook);
            mapModel.put("status", "success");
        }catch (Exception e){
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }
}