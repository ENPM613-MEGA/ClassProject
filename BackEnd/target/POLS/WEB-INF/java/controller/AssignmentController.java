package controller;

import DAO.AssignmentDAO;
import domain.Assignment;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    private AssignmentDAO assignmentDAO;
    private Validator validator;

    @Autowired
    public void setValidator(Validator validator){this.validator = validator;}

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Object> createAssignment(HttpServletRequest request) {

        Map<String, Object> mapMpdel = new HashMap<>();
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
                mapMpdel.put("status", "success");
            }catch (Exception e) {
                e.printStackTrace();
                mapMpdel = POLSHelper.failureReturnConstructor(e.getMessage());
            }
        }else {
            errorMessage += "assignmentName already exists! try delete it first!";
            System.out.println(errorMessage);
            return POLSHelper.failureReturnConstructor(errorMessage);
        }

        return mapMpdel;
    }

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
}
