package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Validator {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final String CHECK_ACCOUNT_BY_ID = "SELECT count(*) FROM Users " +
                                               "WHERE " +
                                               "id = ?";

    public boolean isIdExisted(int id) {
        return jdbcTemplate.queryForObject(CHECK_ACCOUNT_BY_ID, Integer.class, id) > 0;
    }
}
