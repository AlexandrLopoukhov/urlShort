package urlShort.results;

import java.util.Map;

/**
 * Created by admin on 12/9/2016.
 */
public class StaticticResult {
    Map<String, Integer> statisticMap;
    String re;

    public String getRe() {
        return re;
    }

    public StaticticResult(Exception e) {
        re = e.toString();

    }

    public StaticticResult(Map<String, Integer> statisticMap) {
        this.statisticMap = statisticMap;


    }

    public Map<String, Integer> getStatisticMap() {
        return statisticMap;
    }
}


