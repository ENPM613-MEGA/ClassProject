package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class POLSClassRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        POLSClass.POLSClassBuilder polsClassBuilder = new POLSClass.POLSClassBuilder();
        return polsClassBuilder.setId(rs.getInt("id"))
                               .setClassname(rs.getString("class_name"))
                               .setDescription(rs.getString("description"))
                               .setStartDate(rs.getDate("start_date"))
                               .setEndDate(rs.getDate("end_date"))
                               .setInstructor(rs.getInt("instructor_id"))
                               .build();
    }
}
