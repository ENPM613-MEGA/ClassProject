package controller;

import DAO.AccountDAO;
import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountDAO accountDAO;

    @Autowired
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @RequestMapping(value = "/login/{username}")
    public Account login(@PathVariable String username) {
         Account account = accountDAO.getAccountByLogin(username);
         return account;
    }
}
