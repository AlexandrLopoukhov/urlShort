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
        jdbcTemplate.update("INSERT INTO " + ACCOUNT_TABLE + " VALUES(?, ?)", account.getAccountId(),account.getPassword());

    }

    public static String getAccountPassword(Account account) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        String password = null;
        password = jdbcTemplate.queryForObject("SELECT PASSWORD FROM " + ACCOUNT_TABLE + " WHERE ID = ?", String.class, account.getAccountId());
        return password;
    }

    public static int getAccountByPassword(String password) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int accountId = 0;
        accountId = jdbcTemplate.queryForObject("SELECT ID FROM " + ACCOUNT_TABLE + " WHERE PASSWORD = ?", Integer.class, password);
        return accountId;
    }
}
