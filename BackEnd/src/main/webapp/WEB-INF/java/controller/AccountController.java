package controller;

import DAO.AccountDAO;
import domain.Account;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import utils.JSONHelper;
import utils.POLSHelper;
import utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountDAO accountDAO;
    private Validator validator;

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /*login -frank
    *
    * take the [username] and [passwd] as parameters,
    * query for the account info by the username, and compare the passwd
    * */
    @RequestMapping(value = "/login/{username}/{passwd}", method = RequestMethod.GET)
    public Map<String, Object> login(@PathVariable String username, @PathVariable String passwd) {
        Map<String, Object> mapModel = new HashMap<>();

        try {
            Account account = accountDAO.getAccountByUsername(username);
            if (account.getPasswd().equals(passwd)) {
                mapModel.put("status", "success");
                mapModel.put("userProfile", account);
                //TODO: add userCourses
                //TODO: add userAssignments
                //TODO: add userGrades
            }else {
                 mapModel = POLSHelper.failureReturnConstructor("password incorrect");
            }
         }catch (EmptyResultDataAccessException e) {
            mapModel = POLSHelper.failureReturnConstructor("user not exists");
        }catch (DataAccessException e) {
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * register a new account
    * the [username] [passwd] [gender] [role] are required
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registerNewAccount(@RequestBody Account.AccountBuilder accountBuilder) {
        Map<String, Object> mapModel = new HashMap<>();
        Account account = accountBuilder.build();

        // Check username existence
        // new account doesn't have id
        try {
            accountDAO.getAccountByUsername(account.getUsername());
            return POLSHelper.failureReturnConstructor("username already exists!");
        }catch (EmptyResultDataAccessException e) {
            // if username not exist, continue the process
        }catch (Exception e) {
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        try{
            Account newAccount = accountDAO.createNewAccount(account);
            mapModel.put("status", "success");
            mapModel.put("userProfile", newAccount);
        }catch (Exception e) {
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * Update [passwd, email, birth, addr, colorBlind]
    * Overwrite the original account data
    * */
    @RequestMapping(value = "/update-account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateAccount(@RequestBody Account.AccountBuilder accountBuilder) {
        Map<String, Object> mapModel = new HashMap<>();
        Account account = accountBuilder.build();

        //check user exists
        if (!validator.isIdExisted(account.getId())) {
            return POLSHelper.failureReturnConstructor("user not exists!");
        }

        try{

            accountDAO.updateAccount(account);
            mapModel.put("status", "success");
        }catch (Exception e) {
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * Update [points] of a specific account based on its [id]
    * */
    @RequestMapping(value = "/update-point", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatePointOfAccount(HttpServletRequest rq) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(rq);

        if (!validator.isIdExisted(input.getInt("id"))) {
            return POLSHelper.failureReturnConstructor("user not exists!");
        }

        try{
            accountDAO.updatePointsOfAccount(input.getInt("id"), input.getInt("points"));
            mapModel.put("status", "success");
        }catch (Exception e){
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }
}
