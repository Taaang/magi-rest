package priv.taaang.taaang.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TdaQ on 16/12/12.
 */
public class RouteMap {

    private Map<String, Router> mRouteMapping = new HashMap<>();

    public void register(String baseUrl, Class<? extends Router> router) {
        
    }
}
