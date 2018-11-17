package DAO;


import domain.Account;
import domain.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import utils.Validator;

import java.util.ArrayList;
import java.util.Map;

@Repository
public class AccountDAO {
    private JdbcTemplate jdbcTemplate;
    private Validator validator;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}

    private final String GET_AN_ACCOUNT_SQL = "SELECT id, username, password, gender, role," +
            "birth, points, addr, email, color_blind FROM Users WHERE username = ?";

    /*
    * Used for login
    * */
    public Account getAccountByUsername(String username) {
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


    private final String CREATE_NEW_ACCOUNT = "INSERT INTO Users " +
                                            "(username, password, email, gender, birth, role, addr, points, color_blind)" +
                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    /*
    * create a new account
    * the [username] [passwd] [gender] [role] are required
    * */
    public Account createNewAccount(Account account) {

        // Check username existence
        // new account doesn't have id
        try {
            getAccountByUsername(account.getUsername());
            throw new RuntimeException("Username already exists!");
        }catch (EmptyResultDataAccessException e) {
            // if username not exist, continue the process
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        Object[] colums = new Object[9];
        colums[0] = account.getUsername();
        colums[1] = account.getPasswd();
        colums[2] = account.getEmail();
        colums[3] = account.getGender();
        colums[4] = account.getBirth();
        colums[5] = account.getRole();
        colums[6] = account.getAddress();
        colums[7] = account.getPoints() == null ? 0 : account.getPoints(); // default 0
        colums[8] = account.getColorBlind() == null ? false : account.getColorBlind(); // default 0
        try {
            jdbcTemplate.update(CREATE_NEW_ACCOUNT, colums);
            System.out.println("INSERT success!");
            return getAccountByUsername(account.getUsername());
        }catch (Exception e){
            System.out.println("INSERT error!");
            throw e;
        }
    }

    private final String UPDATE_ACCOUNT = "UPDATE Users " +
                                         "SET " +
                                         "password = ?, email = ?, birth = ?, addr = ?, color_blind = ? " +
                                         "WHERE id = ?";
    /*
     * Update an existed account
     * only [passwd, email, birth, addr, colorBlind] can be updated
     * */
    public void updateAccount(Account account) {

        if (!validator.isIdExisted(account.getId())) {
            throw new RuntimeException("user not exists!");
        }

        Object[] colums = new Object[6];
        colums[0] = account.getPasswd();
        colums[1] = account.getEmail();
        colums[2] = account.getBirth();
        colums[3] = account.getAddress();
        colums[4] = account.getColorBlind();
        colums[5] = account.getId();
        try {
            jdbcTemplate.update(UPDATE_ACCOUNT, colums);
            System.out.println("UPDATE success!");
        }catch (Exception e){
            System.out.println("UPDATE error!");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private final String UPDATE_POINTS_BY_ACCOUNT = "UPDATE Users " +
                                                   "SET " +
                                                   "points = ? " +
                                                   "WHERE id = ?";
    /*
    * Update the points of a specific user
    * */
    public void updatePointsOfAccount(int id, int point) {

        if (!validator.isIdExisted(id)) {
            throw new RuntimeException("user not exists!");
        }

        try{
            jdbcTemplate.update(UPDATE_POINTS_BY_ACCOUNT, new Object[]{point, id});
            System.out.println("UPDATE the points of account success!");
        }catch (Exception e) {
            System.out.println("UPDATE the points of account error!");
            System.out.println(e.getMessage());
            throw e;
        }
    }


    private final String GET_ROLE_BY_UID = "SELECT role FROM Users " +
                                           "WHERE id = ?";
    public String getRoleByUid(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_ROLE_BY_UID, new Object[]{id}, String.class);
        }catch (Exception e) {
            System.out.println("Can't get role by id!");
        }
        return null;
    }
}
