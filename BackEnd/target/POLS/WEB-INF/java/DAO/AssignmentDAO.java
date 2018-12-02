package DAO;

import domain.Assignment;
import domain.AssignmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class AssignmentDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}


    private final String CREATE_AN_ASSIGNMENT = "INSERT INTO Assignments " +
                                                "(c_id, ass_name, path, create_date, due_date, publish) " +
                                                "VALUES (?, ?, ?, ?, ?, ?)";
    /*
    * keep the record of an assignment
    * */
    public void createAssignment(Assignment assignment) {
        SimpleDateFormat dfCreateDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfDueDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Object[] inputs = new Object[6];
        inputs[0] = assignment.getClassId();
        inputs[1] = assignment.getName();
        inputs[2] = assignment.getPath();
        inputs[3] = dfCreateDate.format(assignment.getCreateDate());
        inputs[4] = dfDueDate.format(assignment.getDueDate());
        inputs[5] = assignment.getPublish();

        try{
            jdbcTemplate.update(CREATE_AN_ASSIGNMENT, inputs);
        }catch (Exception e) {
            throw e;
        }
    }

    private final String GET_ASS_BY_ID = "SELECT id, c_id, ass_name, path, create_date, due_date, publish " +
                                         "FROM Assignments WHERE id = ?";
    /*
    * Get assignment by Id
    * */
    public Assignment getAssignmentById(int id) {
        try {
            return (Assignment)jdbcTemplate.queryForObject(GET_ASS_BY_ID, new Object[]{id}, new AssignmentRowMapper());
        }catch (Exception e) {
            throw e;
        }
    }


    private final String DELETE_ASSIGNMENT_BY_ID = "DELETE FROM Assignments WHERE id = ?";

    /*
    * delete an assignment record by id
    * */
    public void deleteAssignmentById(int id) {
        try{
            jdbcTemplate.update(DELETE_ASSIGNMENT_BY_ID, new Object[]{id});
        }catch (Exception e) {
            throw e;
        }
    }

    private final String UPDATE_ASSIGNMENT = "UPDATE Assignments " +
                                             "SET c_id = ?, ass_name = ?, path = ?, create_date = ?, due_date = ?, publish = ? " +
                                             "WHERE id = ?";
    /*
    * update an assignment record
    * */
    public void updateAssignment(Assignment assignment) {
        SimpleDateFormat dfCreateDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfDueDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Object[] inputs = new Object[7];
        inputs[0] = assignment.getClassId();
        inputs[1] = assignment.getName();
        inputs[2] = assignment.getPath();
        inputs[3] = dfCreateDate.format(assignment.getCreateDate());
        inputs[4] = dfDueDate.format(assignment.getDueDate());
        inputs[5] = assignment.getPublish();
        inputs[6] = assignment.getId();

        try{
            jdbcTemplate.update(UPDATE_ASSIGNMENT, inputs);
        }catch (Exception e) {
            throw e;
        }
    }


    private final  String GET_ASS_LIST_OF_CLASS = "SELECT id, c_id, ass_name, path, create_date, due_date, publish " +
                                                  "FROM Assignments WHERE c_id = ?";
    /*
    * get assignment list of a class
    * */
    public List<Assignment> getAssListByClass(int cId) throws ParseException {

        SimpleDateFormat createDf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dueDf = new SimpleDateFormat("");
        List<Assignment> res = new ArrayList<>();
        try {
             List<Map<String, Object>> list = jdbcTemplate.queryForList(GET_ASS_LIST_OF_CLASS, new Object[]{cId});
             for (Map<String, Object> map : list) {
                 Assignment tmp = new Assignment.AssignmentBuilder((Integer) map.get("c_id"), (String) map.get("ass_name"))
                                                .setId((Integer) map.get("id"))
                                                .setPath((String) map.get("path"))
                                                .setCreateDate((Date) map.get("create_date"))
                                                .setDueDate((Date) map.get("due_date"))
                                                .setPublish((Boolean) map.get("publish"))
                                                .build();
                 res.add(tmp);
             }
        }catch (Exception e) {
            throw e;
        }
        return res;
    }

    private final String GET_CLASS_ASSIGNMENTLIST = "SELECT id, ass_name, create_date, due_date, publish " +
                                                    "FROM Assignments " +
                                                    "WHERE c_id = ?";
    /*
    * get assignments list of a class
    * */
    public List<Map<String, Object>> getClassAssignmentsList(int cId) {
        try {
            return jdbcTemplate.queryForList(GET_CLASS_ASSIGNMENTLIST, new Object[]{cId});
        }catch (Exception e) {
            throw e;
        }
    }
}
