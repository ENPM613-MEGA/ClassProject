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

    /*
    * Check based on user id that a user is an instructor
    * */
    public boolean isIntructor(int id) {
        return accountDAO.getRoleByUid(id).toLowerCase().equals("instructor");
    }


    private final String CHECK_USER_BELONG_CLASS = "SELECT count(*) FROM User_Class " +
                                                   "WHERE " +
                                                   "u_id = ? AND c_id = ?";
    /*
    * Check if a user is member of a class
    * */
    public boolean isMemberOfClass(Integer uId, Integer cId) {
        return jdbcTemplate.queryForObject(CHECK_USER_BELONG_CLASS, Integer.class, uId, cId) > 0;
    }

    /*
    * check the input type of file is valid
    * [syllabus, file, video]
    * */
    public boolean isFileTypeValid(String type) {
        String str = type.toLowerCase();
        return str.equals("syllabus") || str.equals("file") || str.equals("video");
    }
}
