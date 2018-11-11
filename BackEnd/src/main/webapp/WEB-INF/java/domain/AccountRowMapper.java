package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setBirth(rs.getDate("birth"));
        account.setGender(rs.getString("gender"));
        account.setPasswd(rs.getString("password"));
        account.setRole(rs.getString("role"));
        account.setPoints(rs.getInt("points"));
        account.setUsername(rs.getString("username"));
        return account;
    }
}
