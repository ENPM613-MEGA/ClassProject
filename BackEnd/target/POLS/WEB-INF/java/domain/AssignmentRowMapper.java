package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Assignment.AssignmentBuilder assignmentBuilder = new Assignment.AssignmentBuilder(rs.getInt("c_id"), rs.getString("ass_name"));
        Assignment assignment = assignmentBuilder.setId(rs.getInt("id"))
                                                 .setPath(rs.getString("path"))
                                                 .setDueDate(rs.getDate("due_date"))
                                                 .setCreateDate(rs.getDate("create_date"))
                                                 .setPublish(rs.getBoolean("publish"))
                                                 .build();
        return assignment;
    }
}
