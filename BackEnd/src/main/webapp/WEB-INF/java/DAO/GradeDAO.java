package DAO;


import domain.Grade;
import domain.GradeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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


    private final String GET_GRADE_SQL = "SELECT grade, u_id, a_id FROM Grades WHERE u_id = ? AND a_id = ?";

    /**
     * Query grade
     * @param u_id, a_id
     * @return grade
     */
    public Grade getGrade(int u_id, int a_id) {

        try {
            Grade grade = (Grade)jdbcTemplate.queryForObject(GET_GRADE_SQL, new Object[]{u_id, a_id}, new GradeRowMapper());
            return grade;
        } catch (DataAccessException e) {
            throw e;
        }
    }


    private final String UPDATE_GRADE = "UPDATE Grades SET grade = ? WHERE u_id = ? AND a_id = ?";

    /**
     * Update grade
     * @param u_id, a_id, grade
     * @return
     */
    public void updateGrade(int u_id, int a_id, int grade) {
        try{
            jdbcTemplate.update(UPDATE_GRADE, new Object[]{grade, u_id, a_id});
        }catch (Exception e) {
            throw e;
        }
    }


    private final String GET_STUDENT_GARDES_OF_ASS = "SELECT Users.id, Users.username, Grades.grade " +
                                                     "FROM Users, Grades " +
                                                     "WHERE Users.id = Grades.u_id AND Grades.a_id = ?";
    /*
    * get student grades list of each assignment
    * */
    public List<Map<String, Object>> getClassStudentsGrades(Integer assId) {
        try {
            return jdbcTemplate.queryForList(GET_STUDENT_GARDES_OF_ASS, new Object[]{assId});
        }catch (Exception e){
            throw e;
        }
    }


    private final String GET_STUDENT_GRADES_BOOK = "SELECT Assignments.ass_name, Assignments.id, Grades.grade FROM Grades, Assignments " +
                                                   "WHERE Grades.a_id = Assignments.id AND Grades.u_id = ?";
    /*
    * get assignment grades of each student
    * */
    public List<Map<String, Object>> getStudentGrades(int uId) {
        try {
            return jdbcTemplate.queryForList(GET_STUDENT_GRADES_BOOK, new Object[]{uId});
        }catch (Exception e) {
            throw e;
        }
    }
}
