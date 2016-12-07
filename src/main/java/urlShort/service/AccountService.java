package urlShort.service;


import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.model.Account;
import urlShort.utils.DBInitializator;

/**
 * Created by admin on 12/6/2016.
 */
public class AccountService {
    private static final String ACCOUNT_TABLE = "ACCOUNT";

    public static void saveAccount(Account account){
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO " + ACCOUNT_TABLE + " VALUES(?, ?)", account.getAccountId(),"PWD" /*account.getPassword()*/);

    }

}
