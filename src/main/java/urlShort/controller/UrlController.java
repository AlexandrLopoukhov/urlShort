package urlShort.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urlShort.model.Account;
import urlShort.service.AccountService;

/**
 * Created by admin on 12/7/2016.
 */
@RestController
public class UrlController {
    @RequestMapping(value = "register", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ShortUrlResult registerShortUrl(@RequestHeader Account account, @RequestBody UrlData urlData){
        ShortUrlResult shortUrlResult = null;
        if (!account.getPassword().equals(AccountService.getAccountPassword(account)) && account.getPassword() != null) {
            
        }
        return shortUrlResult;

    }
}
