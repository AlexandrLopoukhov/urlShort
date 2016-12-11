package urlShort.results;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 12/9/2016.
 */
public class StaticticResult {
    String statisticMap;


    public StaticticResult(Exception e) {

    }

    public StaticticResult(Map<String, Integer> statisticMap) {
        try {
            this.statisticMap = new ObjectMapper().writeValueAsString(statisticMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStatisticMap() {
        return statisticMap;
    }
}


