package priv.taaang.magi.application;

import priv.taaang.magi.exception.MagiRestDefaultException;
import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.parser.BodyParser;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.request.ServletRequest;
import priv.taaang.magi.response.Response;
import priv.taaang.magi.response.ServletResponse;
import priv.taaang.magi.route.Entry;
import priv.taaang.magi.route.Router;
import priv.taaang.magi.util.Preconditions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by TdaQ on 16/12/12.
 */
public class MagiApplication {

    private Map<String, Router> mRouteMapping = new HashMap<>();

    public void registerRouter(String baseRoute, Class clazz) {
        try {
            mRouteMapping.put(baseRoute, Router.buildFromClass(baseRoute, clazz));
        } catch (IllegalAccessException | InstantiationException e) {
            throw new MagiRestDefaultException(e);
        }
    }

    public void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws RequestMappingNotFoundException{
        Request request = new ServletRequest(servletRequest);
        Response response = new ServletResponse(servletResponse);

        Optional<Router> matchedRouter = match(request.getRequestRoute());
        Preconditions.when(!matchedRouter.isPresent()).throwException(RequestMappingNotFoundException.class, request.getRequestRoute());

        try {
            matchedRouter.get().invoke(request, response);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new MagiRestDefaultException(e);
        }
    }

    private Optional<Router> match(String requestRoute) {
        Optional<String> matchedMapping = mRouteMapping.keySet().stream().filter(requestRoute::contains).findFirst();
        if (matchedMapping.isPresent()) {
            return Optional.of(mRouteMapping.get(matchedMapping.get()));
        }
        return Optional.empty();
    }
}
