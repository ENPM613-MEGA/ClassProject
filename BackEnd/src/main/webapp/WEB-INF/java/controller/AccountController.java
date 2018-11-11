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

    @ResponseBody
    @RequestMapping(value = "/login/{username}")
    public String login(@PathVariable String username) {
         Account account = accountDAO.getAccountByLogin(username);
         return account.getUsername() + account.getGender() + account.getRole();
    }
}
