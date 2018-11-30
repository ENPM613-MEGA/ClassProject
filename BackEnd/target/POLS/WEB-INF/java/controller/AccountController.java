package controller;

import DAO.AccountDAO;
import DAO.AssignmentDAO;
import DAO.POLSClassDAO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountDAO accountDAO;
    private Validator validator;
    private POLSClassDAO polsClassDAO;
    private AssignmentDAO assignmentDAO;

    @Autowired
    public void setAssignmentDAO(AssignmentDAO assignmentDAO){this.assignmentDAO = assignmentDAO;}

    @Autowired
    public void setPolsClassDAO(POLSClassDAO polsClassDAO){this.polsClassDAO = polsClassDAO;}

    @Autowired
    public void setValidator(Validator validator) {this.validator = validator;}

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /*login -frank
    *
    * take the [username, passwd] as parameters,
    * query for the account info by the username, and compare the passwd
    * */
    @RequestMapping(value = "/login/{username}&{passwd}", method = RequestMethod.GET)
    public Map<String, Object> login(@PathVariable String username, @PathVariable String passwd) {
        Map<String, Object> mapModel = new HashMap<>();

        try {
            Account account = accountDAO.getAccountByUsername(username);
            if (account.getPasswd().equals(passwd)) {
                mapModel.put("status", "success");
                mapModel.put("token", POLSHelper.generateToken());
                Account returnAccount = new Account.AccountBuilder(account.getUsername(), account.getGender(), account.getRole())
                        .setId(account.getId())
                        .setEmail(account.getEmail())
                        .setAddress(account.getAddress())
                        .setColorBlind(account.getColorBlind())
                        .setBirth(account.getBirth())
                        .setPoints(account.getPoints())
                        .build();
                mapModel.put("userProfile", returnAccount);
                //add classes that the user enrolled in
                List<Map<String, Object>> classList = polsClassDAO.getClassList(returnAccount.getId());
                mapModel.put("classes", classList);
                //add userAssignments
                List<List<Map<String, Object>>> assList = new ArrayList<>();
                for (Map<String, Object> POLSclass : classList) {
                    List<Map<String, Object>> list = assignmentDAO.getClassAssignmentsList((Integer) POLSclass.get("id"));
                    if (list.size() == 0) continue;
                    assList.add(list);
                }
                mapModel.put("assignmentLists", assList);

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
    * the [username, passwd, gender, role] are required
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registerNewAccount(@RequestBody Account.AccountBuilder accountBuilder) {
        Map<String, Object> mapModel = new HashMap<>();
        Account account = accountBuilder.build();

        try {// Check username existence
            accountDAO.getAccountByUsername(account.getUsername());
            return POLSHelper.failureReturnConstructor("username already exists!");
        }catch (EmptyResultDataAccessException e) {
            // if username not exist, continue the process
        }catch (Exception e) {
            return POLSHelper.failureReturnConstructor(e.getMessage());
        }

        try{
            Account newAccount = accountDAO.createNewAccount(account);
            mapModel.put("token", POLSHelper.generateToken());
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
    public Map<String, Object> updateAccount(HttpServletRequest request) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(request);

        if (input.getString("token") == null ||
                !validator.isTokenValid(input.getInt("id"), input.getString("token"))) {//check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        //build account object
        Account account = new Account.AccountBuilder().setId(input.getInt("id"))
                                                      .setPasswd(input.getString("passwd"))
                                                      .setEmail(input.getString("email"))
                                                      .setAddress(input.getString("address"))
                                                      .setColorBlind(input.getBoolean("colorBlind"))
                                                      .build();

        if (!validator.isIdExisted(account.getId())) {//check user exists
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
        int id = input.getInt("id");

        if (input.getString("token") == null ||
                !validator.isTokenValid(id, input.getString("token"))) {// check token
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        if (!validator.isIdExisted(id)) {// check account existence
            return POLSHelper.failureReturnConstructor("user not exists!");
        }

        try{
            accountDAO.updatePointsOfAccount(id, input.getInt("points"));
            mapModel.put("status", "success");
        }catch (Exception e){
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /*
    * Get account by userId
    * */
    @RequestMapping(value = "/get-account/{uId}&{token}", method = RequestMethod.GET)
    public Map<String, Object> getAccountById(@PathVariable int uId, @PathVariable String token) {
        Map<String, Object> mapModel = new HashMap<>();

        if(token == null || !validator.isTokenValid(uId, token)) {
            return POLSHelper.failureReturnConstructor("token not match to user!");
        }

        try {
            Account account = accountDAO.getAccountById(uId);
            mapModel.put("account", account);
            mapModel.put("status", "success");
        }catch (Exception e) {
            e.printStackTrace();
            POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }
}
