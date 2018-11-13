package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Account.AccountBuilder accountBuilder = new Account.AccountBuilder(rs.getInt("id"),
                rs.getString("username"), rs.getString("gender"),
                rs.getString("role"));

        Account account = accountBuilder.
                setAddress(rs.getString("addr")).
                setBirth(rs.getDate("birth")).
                setEmail(rs.getString("email")).
                setPasswd(rs.getString("password")).
                setPoints(rs.getInt("points")).
                build();
        return account;
    }
}
