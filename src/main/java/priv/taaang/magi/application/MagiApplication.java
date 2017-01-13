package priv.taaang.magi.application;

import priv.taaang.magi.exception.MagiRestDefaultException;
import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.handler.ExceptionHandler;
import priv.taaang.magi.parser.BodyParser;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.request.ServletRequest;
import priv.taaang.magi.response.Response;
import priv.taaang.magi.response.ServletResponse;
import priv.taaang.magi.route.Router;
import priv.taaang.magi.util.Preconditions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by TdaQ on 16/12/12.
 */
public class MagiApplication {

    private Map<String, Router> mRouteMapping = new HashMap<>();
    private Map<Class<? extends Exception>, ExceptionHandler> mExceptionMapping = new HashMap<>();

    public MagiApplication() {
    }

    public void registerException(Class<? extends Exception> exceptionClazz, ExceptionHandler exceptionHandler) {
        mExceptionMapping.put(exceptionClazz, exceptionHandler);
    }

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
        Object result = new Object();

        try {
            Optional<Router> matchedRouter = match(request.getRequestRoute());
            Preconditions.when(!matchedRouter.isPresent()).throwException(RequestMappingNotFoundException.class, request.getRequestRoute());

            Map<String, Object> bodyParameters = BodyParser.parse(request);
            request.appendParameters(bodyParameters);
            Map<String, Object> pathParameters = request.getRawRequestPathParameters();
            request.appendParameters(pathParameters);

            result = matchedRouter.get().invoke(request, response);
        } catch (Exception e) {
            result = mExceptionMapping.get(e.getClass()).handle(request, response, e);
        } finally {
            response.respond(result);
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
