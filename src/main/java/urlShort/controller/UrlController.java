package urlShort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ShortUrlResult> registerShortUrl(@RequestHeader("Authorization") String password, @RequestBody UrlData urlData) {
        ShortUrlResult shortUrlResult;
        HttpStatus status;
        int accountId = 0;
        try {
            accountId = AccountService.getAccountByPassword(password);
            ShortUrl shortUrl = new ShortUrl(urlData);
            UrlService.saveShortUrl(shortUrl, accountId);
            shortUrlResult = new ShortUrlResult(shortUrl);
            status = HttpStatus.OK;
        } catch (Exception e) {
            shortUrlResult = new ShortUrlResult(e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<ShortUrlResult>(shortUrlResult, status);

    }
}
