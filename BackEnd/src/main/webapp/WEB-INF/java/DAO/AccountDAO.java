package DAO;


import domain.Account;
import domain.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final String GET_AN_ACCOUNT_SQL = "SELECT id, username, password, gender, role," +
            "birth, points, addr, email FROM Users WHERE username = ?";

    public Account getAccountByLogin(String username) {
        try {
            Account account = (Account) jdbcTemplate.queryForObject(GET_AN_ACCOUNT_SQL, new Object[]{username},
                    new AccountRowMapper());
            return account;
        }catch (EmptyResultDataAccessException e) {
            System.out.println("No such username exists");
            throw e;
        }catch (DataAccessException e) {
            System.out.println("Database connection error");
        }

        return null;
    }
}
