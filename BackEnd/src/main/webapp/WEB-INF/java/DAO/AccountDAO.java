package DAO;


import domain.Account;
import domain.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import utils.Validator;

import java.util.List;
import java.util.Map;


/*
* Serve the communication between server and database of all the Account business logic
* All the serviec provided in DAO level are atomic, the validation should be done at Controller level
* */
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

        Object[] inputs = new Object[9];
        inputs[0] = account.getUsername();
        inputs[1] = account.getPasswd();
        inputs[2] = account.getEmail();
        inputs[3] = account.getGender();
        inputs[4] = account.getBirth();
        inputs[5] = account.getRole();
        inputs[6] = account.getAddress();
        inputs[7] = account.getPoints() == null ? 0 : account.getPoints(); // default 0
        inputs[8] = account.getColorBlind() == null ? false : account.getColorBlind(); // default 0
        try {
            jdbcTemplate.update(CREATE_NEW_ACCOUNT, inputs);
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


        Object[] inputs = new Object[6];
        inputs[0] = account.getPasswd();
        inputs[1] = account.getEmail();
        inputs[2] = account.getBirth();
        inputs[3] = account.getAddress();
        inputs[4] = account.getColorBlind();
        inputs[5] = account.getId();
        try {
            jdbcTemplate.update(UPDATE_ACCOUNT, inputs);
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
    /*
    * Get the role attribute by userId
    * */
    public String getRoleByUid(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_ROLE_BY_UID, new Object[]{id}, String.class);
        }catch (Exception e) {
            System.out.println("Can't get role by id!");
        }
        return null;
    }

    private final String GET_STUDENTS_LIST_BY_CLASS = "SELECT Users.id, Users.username FROM " +
                                                      "Users, User_Class WHERE " +
                                                      "Users.id = User_Class.u_id AND User_Class.c_id = ? " +
                                                      "AND Users.role = 'student'";

    /*
    * Get student list of a class
    * */
    public List<Map<String, Object>> getClassStudentList(int cId) {
        try{
            return jdbcTemplate.queryForList(GET_STUDENTS_LIST_BY_CLASS, new Object[]{cId});
        }catch (Exception e) {
            throw e;
        }
    }

}
