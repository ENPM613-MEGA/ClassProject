package controller;

import DAO.AssignmentDAO;
import DAO.GradeDAO;
import domain.Assignment;
import domain.Problem;
import org.json.JSONArray;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    private AssignmentDAO assignmentDAO;
    private Validator validator;
    private GradeDAO gradeDAO;

    @Autowired
    public void setGradeDAO(GradeDAO gradeDAO) {this.gradeDAO = gradeDAO;}

    @Autowired
    public void setValidator(Validator validator){this.validator = validator;}

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}


    /*
    * create an assignmentf or a class
    * */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Object> createAssignment(HttpServletRequest request) {

        Map<String, Object> mapModel = new HashMap<>();
        JSONObject inputObject = JSONHelper.readJSONObject(request);
        String assignmentName = inputObject.getString("assName");
        Integer uId = inputObject.getInt("uId");
        Integer cId = inputObject.getInt("cId");
        String token = inputObject.getString("token");
        JSONArray data = inputObject.getJSONArray("data");
        String dueDate = inputObject.getString("dueDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //parse format

        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/assignments");
        String destFileName = realPath + "/" + cId + "/" + assignmentName + ".txt";
        File file = new File(destFileName);

        //build assignment object
        Assignment assignment = null;
        try{
             assignment = new Assignment.AssignmentBuilder(cId, assignmentName)
                    .setPath(destFileName)
                    .setPublish(inputObject.getBoolean("publish"))
                    .setCreateDate(new Date())
                    .setDueDate(df.parse(dueDate))
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        String errorMessage = "ERROR: ";

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }


        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, cId)) {// check user privilege
            errorMessage += "Invalid create, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (destFileName.endsWith(File.separator)) { // detined file name invalid
            errorMessage += "The destined file cannot be folder!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        if (!file.getParentFile().exists()) { // parent dir doesn't exists
            System.out.println("Try to create parent folder...");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("Create parent folder fails");
                return POLSHelper.failureReturnConstructor("Cannot find the destined file!");
            }
        }

        //create file
        if (!file.exists()) {
            try{
                file.createNewFile();

                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(data.toString());
                output.close();
                assignmentDAO.createAssignment(assignment);
                mapModel.put("status", "success");
            }catch (Exception e) {
                e.printStackTrace();
                mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
            }
        }else {
            errorMessage += "assignmentName already exists! try delete it first!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        return mapModel;
    }

    /*
    * delete an assignment
    * */
    @RequestMapping(value = "/delete/{assId}&{uId}&{token}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteAssignment(@PathVariable int assId, @PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();
        String errorMessage = "ERROR: ";

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        Assignment assignment = assignmentDAO.getAssignmentById(assId);
        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, assignment.getClassId())) {
            errorMessage += "Invalid delete, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            POLSHelper.failureReturnConstructor(errorMessage);
        }

        File deleteAss = new File(assignment.getPath());
        if (!deleteAss.delete()) { //delete assignment file
            errorMessage += "delete assignment file" + assignment.getName() + " failed!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        try {
            assignmentDAO.deleteAssignmentById(assId);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }

        return mapModel;
    }


    /*
    * Get an assignment
    * */
    @RequestMapping(value = "/get-assignment/{assId}&{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getAssignment(HttpServletResponse response, @PathVariable int assId, @PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();
        String errorMessage = "ERROR: ";

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        Assignment assignment = null;
        try {
            assignment = assignmentDAO.getAssignmentById(assId);
        }catch (Exception e) {
            e.printStackTrace();
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        if (!validator.isMemberOfClass(uId, assignment.getClassId())) {
            errorMessage += "Invalid delete, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            POLSHelper.failureReturnConstructor(errorMessage);
        }

        String questions = "";
        String line = "";
        try {
            FileInputStream fin = new FileInputStream(assignment.getPath());
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(reader);
            while (( line = br.readLine()) != null) {
                questions += line;
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }

        JSONArray jsonArray = new JSONArray(questions);


        List<Problem> problems = new ArrayList<>();
        boolean isInstructor = validator.isIntructor(uId);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!isInstructor && !jsonObject.getBoolean("publish"))
                continue;

            Problem problem = new Problem(jsonObject);
            problems.add(problem);
        }

        mapModel.put("assignment-info", assignment);
        mapModel.put("problems", problems);
        mapModel.put("status", "success");
        return mapModel;
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Map<String, Object> submitAssignment(HttpServletRequest request) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject jsonObject = JSONHelper.readJSONObject(request);
        int uId = jsonObject.getInt("uId");
        int assId = jsonObject.getInt("assId");
        String token = jsonObject.getString("token");

        if(validator.isAssignmentSubmitted(uId, assId)) {
            return POLSHelper.failureReturnConstructor("this assignment has already been submitted!");
        }

        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        Assignment assignment = null;
        try {//get assignment record
            assignment = assignmentDAO.getAssignmentById(assId);
        }catch (Exception e) {
            e.printStackTrace();
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        String questions = "";
        String line = "";
        try {// get assignment content
            FileInputStream fin = new FileInputStream(assignment.getPath());
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(reader);
            while (( line = br.readLine()) != null) {
                questions += line;
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }

        JSONArray problems = new JSONArray(questions);
        JSONArray submitAnswers = jsonObject.getJSONArray("answers");

        int totalGrades = 0;
        int grades = 0;
        for (int i = 0; i < problems.length(); i++) {
            if (i >= submitAnswers.length()) break;
            String subAnswer = submitAnswers.getString(i);
            String correctAnswer = problems.getJSONObject(i).getString("solution");
            char[] subArray = subAnswer.toLowerCase().toCharArray();
            char[] corArray = correctAnswer.toLowerCase().toCharArray();
            Arrays.sort(subArray);
            Arrays.sort(corArray);
            subAnswer = String.valueOf(subArray);
            correctAnswer = String.valueOf(corArray);
            totalGrades += problems.getJSONObject(i).getInt("grade");

            if (subAnswer.equals(correctAnswer)) {
                grades += problems.getJSONObject(i).getInt("grade");
            }
        }

        try {
            gradeDAO.insertGrade(uId, assId, totalGrades);
            mapModel.put("status", "success");
            mapModel.put("grades", grades);
            mapModel.put("total", totalGrades);
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * Update an assignment
    * classId cannot be changed
    * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> updateAssignment(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //parse format
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject jsonObject = JSONHelper.readJSONObject(request);
        int uId = jsonObject.getInt("uId");
        int assId = jsonObject.getInt("assId");
        String token = jsonObject.getString("token");
        String errorMessage = "ERROR: ";
        JSONArray data = jsonObject.getJSONArray("data");


        if (token == null || !validator.isTokenValid(uId, token)) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        Assignment assignment = null;
        try {//get assignment record
            assignment = assignmentDAO.getAssignmentById(assId);
        }catch (Exception e) {
            e.printStackTrace();
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        if (!validator.isIntructor(uId) || !validator.isMemberOfClass(uId, assignment.getClassId())) {// check privilege
            errorMessage += "Invalid delete, user do not have the privilige to upload this file!";
            System.out.println(errorMessage);
            POLSHelper.failureReturnConstructor(errorMessage);
        }

        Assignment newAssignment = null;
        try{
            newAssignment = new Assignment.AssignmentBuilder(assignment.getClassId(), jsonObject.getString("assName"))
                    .setPath(assignment.getPath())
                    .setPublish(jsonObject.getBoolean("publish"))
                    .setCreateDate(new Date())
                    .setDueDate(df.parse(jsonObject.getString("dueDate")))
                    .setId(assignment.getId())
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        File file = new File(newAssignment.getPath());
        if (file.delete()) {
            try{
                file.createNewFile();
                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(data.toString());
                output.close();
                assignmentDAO.updateAssignment(newAssignment);
                mapModel.put("status", "success");
            }catch (Exception e) {
                e.printStackTrace();
                mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
            }
        }
        return mapModel;
    }


}
