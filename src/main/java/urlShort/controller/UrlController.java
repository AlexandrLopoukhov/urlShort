package urlShort.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urlShort.model.Account;
import urlShort.model.ShortUrl;
import urlShort.model.UrlData;
import urlShort.results.ShortUrlResult;
import urlShort.service.AccountService;
import urlShort.service.UrlService;

/**
 * Created by admin on 12/7/2016.
 */
@RestController
public class UrlController {
    @RequestMapping(value = "register", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ShortUrlResult registerShortUrl(@RequestHeader Account Authorization, @RequestBody UrlData urlData){
        ShortUrlResult shortUrlResult = null;
        try{
        if (!Authorization.getPassword().equals(AccountService.getAccountPassword(Authorization)) && (Authorization.getPassword() != null)) {
            throw new Exception("Don't authorized");
        }
        ShortUrl shortUrl = new ShortUrl(urlData);
        UrlService.saveShortUrl(shortUrl, Authorization);
        shortUrlResult = new ShortUrlResult(shortUrl);}
        catch(Exception e) {
            shortUrlResult = new ShortUrlResult(e);
        }
        return shortUrlResult;

    }
}
