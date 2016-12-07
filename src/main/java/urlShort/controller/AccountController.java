package urlShort.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlShort.model.Account;
import urlShort.model.AccountData;
import urlShort.results.AccountResult;
import urlShort.service.AccountService;


/**
 * Created by admin on 12/4/2016.
 */
@RestController
public class AccountController {

    @RequestMapping(value = "account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public AccountResult createAccount(@RequestBody AccountData accountData) {
        AccountResult accountResult;
        try {
            Account account = new Account(accountData);
            AccountService.saveAccount(account);
            accountResult = new AccountResult(account);
        } catch (Exception e) {
            accountResult = new AccountResult(e);
        }

        return accountResult;
    }
}
