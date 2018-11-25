package controller;

import DAO.AssignmentDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JSONHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    private AssignmentDAO assignmentDAO;

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}

    @RequestMapping("/create")
    public Map<String, Object> createAssignment(HttpServletRequest request) {

        JSONObject inputObject = JSONHelper.readJSONObject(request);
        String assName = inputObject.getString("assName");
        Integer uId = inputObject.getInt("uId");
        Integer cId = inputObject.getInt("cId");
        String token = inputObject.getString("token");


        return null;
    }
}
