package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.model.ShortUrl;
import urlShort.utils.DBInitializator;

/**
 * Created by admin on 12/7/2016.
 */
public class UrlService {
    private static final String URL_TABLE = "URLS";

//TODO add redirect type
    public static void saveShortUrl(ShortUrl shortUrl, int accountId) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO " + URL_TABLE + " VALUES(?, ?, ?, ?)", shortUrl.getShortUrl(), shortUrl.getUrl(), shortUrl.getRedirectType(), accountId);
        //StatisticService.initStatisticForUrl(shortUrl.getShortUrl(), accountId);
    }

    public static boolean isShortUrlExist(String shortUrl) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int shortUrlCnt = 0;
        shortUrlCnt = jdbcTemplate.queryForObject("SELECT count(URL) FROM " + URL_TABLE + " WHERE SHORT_URL = ?", Integer.class, shortUrl);
        if (shortUrlCnt > 0) return true;
        else return false;
    }

    public static String getUrlByShortUrl(String shortUrl) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        String url;
        url = jdbcTemplate.queryForObject("SELECT URL FROM " + URL_TABLE + " WHERE SHORT_URL = ?", String.class, shortUrl);
        return url;
    }

    public static int getRedirectType(String shortUrl) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int redirectType;
        redirectType = jdbcTemplate.queryForObject("SELECT REDIRECT_TYPE FROM " + URL_TABLE + " WHERE SHORT_URL = ?", Integer.class, shortUrl);
        return redirectType;
    }

    public static int getIdByShortUrl(String shortUrl) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int id;
        id = jdbcTemplate.queryForObject("SELECT ID FROM " + URL_TABLE + " WHERE SHORT_URL = ?", Integer.class, shortUrl);
        return id;
    }

    //for tests
    public static String getShortUrlById(int id) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        String shortUrl;
        shortUrl = jdbcTemplate.queryForObject("SELECT SHORT_URL FROM " + URL_TABLE + " WHERE ID = ?", String.class, id);
        return shortUrl;
    }
}
