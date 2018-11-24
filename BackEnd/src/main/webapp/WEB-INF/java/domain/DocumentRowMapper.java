package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Document.DocumentBuilder documentBuilder = new Document.DocumentBuilder(rs.getInt("c_id"),
                                        rs.getString("filename"), rs.getString("type"));
        documentBuilder.setPath(rs.getString("path"));
        documentBuilder.setCreateDate(rs.getDate("create_date"));
        documentBuilder.setPublish(rs.getBoolean("publish"));
        documentBuilder.setId(rs.getInt("id"));
        return documentBuilder.build();
    }
}
