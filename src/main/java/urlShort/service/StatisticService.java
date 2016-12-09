package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.utils.DBInitializator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 12/9/2016.
 */
public class StatisticService {
    private static final String STATISTIC_TABLE = "STATISTIC";


    public static Map<String, Integer> getStatistic(int accountId) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        Map<String, Integer> statisticMap = new HashMap<>();
        Map<String, Object> iterateList = new HashMap<>();
        statisticMap.put("1", 1);
        statisticMap.put("2", 2);

        iterateList = jdbcTemplate.queryForMap("SELECT URL, COUNTER FROM " + STATISTIC_TABLE + " WHERE ID = ?", accountId);
        /*for (Map.Entry<String, Object> entry : iterateList.entrySet()) {
            statisticMap.put(entry.getKey(), (Integer) entry.getValue());
        }*/
       /* ListIterator<Map<String, Object>> iterateListIterator = iterateList.listIterator();
        while (iterateListIterator.hasNext()) {
            Map<String, Object> iterateMap = iterateListIterator.next();
            for (Map.Entry<String, Object> entry : iterateMap.entrySet()) {
                statisticMap.put(entry.getKey(), (Integer) entry.getValue());
            }
        }*/
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
        int cnt;
        String url;
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();

        cnt = getCounterByShortUrl(shortUrl) + 1;
        url = UrlService.getUrlByShortUrl(shortUrl);
        jdbcTemplate.update("UPDATE " + STATISTIC_TABLE + " SET COUNTER = ? WHERE URL = ?", cnt, url);

    }


    public static void initStatisticForUrl(String shortUrl, int accountId) {
        String url;
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();

        url = UrlService.getUrlByShortUrl(shortUrl);
        jdbcTemplate.update("INSERT INTO " + STATISTIC_TABLE + " VALUES(?, ?, ?)", 0, url, accountId);
    }
}
