package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.model.ShortUrl;
import urlShort.utils.DBInitializator;

/**
 * Created by admin on 12/7/2016.
 */
public class UrlService {
    private static final String URL_TABLE = "URLS";

    public static void saveShortUrl(ShortUrl shortUrl, int accountId) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO " + URL_TABLE + " VALUES(?, ?, ?)", shortUrl.getShortUrl(), shortUrl.getUrl(), accountId);
    }

    public static boolean isShortUrlExist(String shortUrl) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int shortUrlCnt = 0;
        shortUrlCnt = jdbcTemplate.queryForObject("SELECT count(URL) FROM " + URL_TABLE + " WHERE SHORT_URL = ?", Integer.class, shortUrl);
        if (shortUrlCnt > 0) return true;
        else return false;
    }
}
