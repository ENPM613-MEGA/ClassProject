package DAO;

import domain.Assignment;
import domain.AssignmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

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
}
