package urlShort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlShort.results.StaticticResult;
import urlShort.service.AccountService;
import urlShort.service.StatisticService;

/**
 * Created by admin on 12/9/2016.
 */
@RestController
@RequestMapping
public class StatisticController {
    @RequestMapping(value = "statistic/{accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaticticResult> getStatistic(@RequestHeader("Authorization") String password, @PathVariable int accountId) {
        StaticticResult staticticResult;
        try {
            if (!AccountService.getAccountPassword(accountId).equals(password) || password == null || accountId == 0) {
                throw new Exception("Password don't match or data invalid");
            }
            staticticResult = new StaticticResult(StatisticService.getStatistic(accountId));
            return new ResponseEntity<StaticticResult>(staticticResult, HttpStatus.OK);
        } catch (Exception e) {
            staticticResult = new StaticticResult(e);
            return new ResponseEntity<StaticticResult>(staticticResult, HttpStatus.UNAUTHORIZED);
        }


    }
}
