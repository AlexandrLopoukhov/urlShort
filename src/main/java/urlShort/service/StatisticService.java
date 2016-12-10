package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.utils.DBInitializator;

import java.sql.Date;
import java.util.*;

/**
 * Created by admin on 12/9/2016.
 */
public class StatisticService {
    private static final String STATISTIC_TABLE = "STATISTIC";


    public static Map<String, Integer> getStatistic(int accountId) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        Map<String, Integer> statisticMap = new HashMap<>();
        List<Map<String, Object>> iterateList;
        String url = null;
        int cnt = 0;

        iterateList = jdbcTemplate.queryForList("SELECT URL, COUNT(REDIRECT_DATE) FROM " + STATISTIC_TABLE + " WHERE ID = ? GROUP BY URL", accountId);

        ListIterator<Map<String, Object>> iterateListIterator = iterateList.listIterator();
        while (iterateListIterator.hasNext()) {
            Map<String, Object> iterateMap = iterateListIterator.next();
            for (Map.Entry<String, Object> entry : iterateMap.entrySet()) {
                switch (entry.getKey()) {
                    case "URL" : url = (String) entry.getValue();
                    break;
                    case "COUNT(REDIRECT_DATE)" : cnt =  (int) (long) entry.getValue();
                    break;
                }
            }
            statisticMap.put(url, cnt);
        }
        return statisticMap;
    }

    public static int getCounterByShortUrl(String shortUrl) {
        String url;
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int cnt;

        url = UrlService.getUrlByShortUrl(shortUrl);
        cnt = jdbcTemplate.queryForObject("SELECT COUNTER FROM " + STATISTIC_TABLE + " WHERE URL = ?", Integer.class, url);
        return cnt;

    }

    public static void setRedirected(String shortUrl) {
        //int cnt;
        String url;
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        int id;

       //cnt = getCounterByShortUrl(shortUrl) + 1;
        url = UrlService.getUrlByShortUrl(shortUrl);
        id = UrlService.getIdByShortUrl(shortUrl);
        Date date = new Date(new java.util.Date().getDate());
        jdbcTemplate.update("INSERT INTO " + STATISTIC_TABLE + " VALUES(?, ?, ?)", date, url, id);

    }


    public static void initStatisticForUrl(String shortUrl, int accountId) {
        String url;
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();

        url = UrlService.getUrlByShortUrl(shortUrl);
        jdbcTemplate.update("INSERT INTO " + STATISTIC_TABLE + " VALUES(?, ?, ?)", 0, url, accountId);
    }
}
