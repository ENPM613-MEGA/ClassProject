package controller;

import DAO.AccountDAO;
import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @RequestMapping(value = "/login/{username}/{passwd}")
    public Map<String, Object> login(@PathVariable String username, @PathVariable String passwd) {
        Account account = null;
        Map<String, Object> mapModel = new HashMap<>();

        try {
            account = accountDAO.getAccountByLogin(username);
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
            mapModel.put("status", "failure");
            mapModel.put("reason", "Database connect error");
        }
        return mapModel;
    }
}
