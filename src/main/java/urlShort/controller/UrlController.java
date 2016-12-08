package urlShort.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ShortUrlResult registerShortUrl(@RequestHeader("Authorization") String password, @RequestBody UrlData urlData) {
        ShortUrlResult shortUrlResult;
        int accountId = AccountService.getAccountByPassword(password);
        try {
            if (accountId == 0) {
                throw new Exception("Don't authorized");
            }
            ShortUrl shortUrl = new ShortUrl(urlData);
            UrlService.saveShortUrl(shortUrl, accountId);
            shortUrlResult = new ShortUrlResult(shortUrl);
        } catch (Exception e) {
            shortUrlResult = new ShortUrlResult(e);
        }
        return shortUrlResult;

    }
}
