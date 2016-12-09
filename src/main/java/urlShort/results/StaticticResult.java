package urlShort.results;

import java.util.Map;

/**
 * Created by admin on 12/9/2016.
 */
public class StaticticResult {
    Map<String, Integer> statisticMap;
    String error = null;

    public String getError() {
        return error;
    }

    public StaticticResult(Exception e) {

        this.error = e.toString();
    }

    public StaticticResult(Map<String, Integer> statisticMap) {


    }

    public Map<String, Integer> getStatisticMap() {
        return statisticMap;
    }
}


