package utils;

import DAO.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Validator {
    private JdbcTemplate jdbcTemplate;
    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final String CHECK_ACCOUNT_BY_ID = "SELECT count(*) FROM Users " +
                                               "WHERE " +
                                               "id = ?";

    /*
    * Check if a user exists
    * */
    public boolean isIdExisted(int id) {
        return jdbcTemplate.queryForObject(CHECK_ACCOUNT_BY_ID, Integer.class, id) > 0;
    }

    public boolean isIntructor(int id) {
        return accountDAO.getRoleByUid(id).toLowerCase().equals("instructor");
    }


    private final String CHECK_USER_BELONG_CLASS = "SELECT count(*) FROM User_Class " +
                                                   "WHERE " +
                                                   "uId = ? AND cId = ?";

    /*
    * Check if a user is member of a class
    * */
    public boolean isMemberOfClass(Integer uId, Integer cId) {
        return jdbcTemplate.queryForObject(CHECK_USER_BELONG_CLASS, Integer.class, uId, cId) > 0;
    }
}
