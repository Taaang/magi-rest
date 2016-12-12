package priv.taaang.taaang.application;

import priv.taaang.taaang.handler.Handler;
import priv.taaang.taaang.handler.HandlerConstructor;
import priv.taaang.taaang.router.RouteMap;
import priv.taaang.taaang.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TdaQ on 16/12/12.
 */
public class TaaangApplication {

    private final Handler mHandler;
    private RouteMap mRouteMapping = new RouteMap();

    public TaaangApplication(HandlerConstructor constructor) {
        mHandler = constructor.create();
    }

    public void registerRouter(String baseRoute, Class<? extends Router> router) {

    }

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        mHandler.handle(request, response);
    }
}
