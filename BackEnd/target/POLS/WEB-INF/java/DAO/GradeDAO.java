package DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GradeDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    private final String INSERT_ASSIGNMENT_GRADE = "INSERT INTO Grades (u_id, a_id, grade) " +
                                                    "VALUES (?, ?, ?)";
    /*
    * Insert Assignment Grade of a user
    * */
    public void insertGrade(int uId, int assId, int grade) {
        try {
            jdbcTemplate.update(INSERT_ASSIGNMENT_GRADE, new Object[]{uId, assId, grade});
        }catch (Exception e) {
            throw e;
        }
    }
}
