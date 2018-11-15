package controller;

import DAO.AccountDAO;
import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountDAO accountDAO;

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
                mapModel.put("status", "failure");
                mapModel.put("reason", "password incorrect");
            }
         }catch (EmptyResultDataAccessException e) {
            mapModel.put("status", "failure");
            mapModel.put("reason", "Account not exist");
        }catch (DataAccessException e) {
            mapModel.put("status", "fail");
            mapModel.put("reason", "Database connect error");
        }
        return mapModel;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registerNewAccount(@RequestBody Account.AccountBuilder accountBuilder) {
        Map<String, Object> mapModel = new HashMap<>();
        try{
            Account account = accountBuilder.build();
            Account newAccount = accountDAO.createNewAccount(account);
            mapModel.put("status", "success");
            mapModel.put("userProfile", newAccount);
        }catch (Exception e) {
            mapModel.put("status", "failure");
            mapModel.put("reason", e.getMessage());
        }
        return mapModel;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateAccount(@RequestBody Account.AccountBuilder accountBuilder) {
        Map<String, Object> mapModel = new HashMap<>();
        try{
            Account account = accountBuilder.build();
            accountDAO.updateAccount(account);
            mapModel.put("status", "success");
        }catch (Exception e) {
            mapModel.put("status", "failure");
            mapModel.put("reason", e.getMessage());
        }
        return mapModel;
    }
}
