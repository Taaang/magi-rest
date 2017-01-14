package priv.taaang.magi.application;

import priv.taaang.magi.exception.MagiRestDefaultException;
import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.filter.Filter;
import priv.taaang.magi.filter.FilterChain;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by TdaQ on 16/12/12.
 */
public class MagiApplication {

    private Map<String, Router> mRouteMapping = new HashMap<>();
    private Map<Class<? extends Exception>, ExceptionHandler> mExceptionMapping = new HashMap<>();
    private FilterChain mFilterChain = new FilterChain();

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

    public void registerFilter(Filter filter) {
        mFilterChain.registerGlobalFilter(filter);
    }

    public void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Request request = new ServletRequest(servletRequest);
        Response response = new ServletResponse(servletResponse);
        Object result = new Object();
        Optional<Router> matchedRouter = Optional.empty();

        try {
            matchedRouter = match(request.getRequestRoute());
            Preconditions.when(!matchedRouter.isPresent()).throwException(RequestMappingNotFoundException.class, request.getRequestRoute());

            Map<String, Object> pathParameters = request.getRawRequestPathParameters();
            request.appendParameters(pathParameters);

            mFilterChain.invokeFilterBefore(request, response, matchedRouter.get());
            result = matchedRouter.get().invoke(request, response);
            mFilterChain.invokeFilterAfter(request, response, matchedRouter.get());
        } catch (Exception e) {
            result = handleException(e, request, response);
        } finally {
            mFilterChain.invokeFilterComplete(request, response, matchedRouter.orElse(null));
            try {
                response.respond(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Optional<Router> match(String requestRoute) {
        Optional<String> matchedMapping = mRouteMapping.keySet().stream().filter(requestRoute::contains).findFirst();
        if (matchedMapping.isPresent()) {
            return Optional.of(mRouteMapping.get(matchedMapping.get()));
        }
        return Optional.empty();
    }

    private Object handleException(Exception e, Request request, Response response) {
        if (mExceptionMapping.containsKey(e.getClass())) {
            return mExceptionMapping.get(e.getClass()).handle(e, request, response);
        }
        throw new MagiRestDefaultException(e);
    }


}
