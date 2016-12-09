package urlShort.service;

import org.springframework.jdbc.core.JdbcTemplate;
import urlShort.utils.DBInitializator;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by admin on 12/9/2016.
 */
public class StatisticService {
    private static final String STATISTIC_TABLE = "STATISTIC";


    public static Map<String, Integer> getStatistic(int accountId) {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        Map<String, Integer> statisticMap = null;
        List<Map<String, Object>> iterateList;

        iterateList = jdbcTemplate.queryForList("SELECT URL, COUNTER FROM " + STATISTIC_TABLE + " WHERE ID = ?",  accountId);
        ListIterator<Map<String, Object>> iterateListIterator = iterateList.listIterator();
        while (iterateListIterator.hasNext()) {
            Map<String, Object> iterateMap = iterateListIterator.next();
            for (Map.Entry<String, Object> entry : iterateMap.entrySet()){
                statisticMap.put(entry.getKey(), Integer.valueOf(entry.getKey()));
            }
        }

        return statisticMap;
    }
}
