package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.model.Account;
import urlShort.model.ShortUrl;
import urlShort.utils.DBInitializator;

/**
 * Created by admin on 12/7/2016.
 */
public class UrlService {
    private static final String URL_TABLE = "URLS";

    public static void saveShortUrl(ShortUrl shortUrl, Account account) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO " + URL_TABLE + " VALUES(?, ?)", );
    }
}
