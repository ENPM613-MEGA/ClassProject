package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Grade grade = new Grade();
        grade.setUserId(rs.getInt("u_id"));
        grade.setAssignmentId(rs.getInt("a_id"));
        grade.setGrade(rs.getInt("grade"));
        return grade;
    }
}
