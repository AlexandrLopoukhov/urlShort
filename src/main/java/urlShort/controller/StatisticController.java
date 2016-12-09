package urlShort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlShort.results.StaticticResult;

/**
 * Created by admin on 12/9/2016.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {
    @RequestMapping(value = "statistic/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<StaticticResult> getStatistic(@PathVariable String accountId){
        StaticticResult staticticResult = new StaticticResult(accountId);
        return new ResponseEntity<StaticticResult>(staticticResult, HttpStatus.OK);

    }
}
