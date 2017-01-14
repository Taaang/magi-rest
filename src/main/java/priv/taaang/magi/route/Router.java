package priv.taaang.magi.route;

import priv.taaang.magi.exception.MethodInvokeException;
import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;
import priv.taaang.magi.util.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by TdaQ on 16/12/12.
 */
public class Router {

    private String mBaseRoute;
    private Class mRouterClazz;
    private Object mRouterInstance;
    private Map<String, Entry> mEntryMapping = new HashMap<>();

    private Router() { }

    public Class getRouterClass() {
        return mRouterClazz;
    }

    public static Router buildFromClass(String baseRoute, Class clazz) throws IllegalAccessException, InstantiationException {
        Router router = new Router();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method each : methods) {
            Entry entry = new Entry(each);
            router.mEntryMapping.put(entry.getEntryRoute(), entry);
        }
        router.mBaseRoute = baseRoute;
        router.mRouterClazz = clazz;
        router.mRouterInstance = clazz.newInstance();
        return router;
    }

    private Optional<Entry> matchEntry(String requestRoute) {
        String entryRoute = requestRoute.replace(mBaseRoute, "");
        Optional<String> entryMapping = mEntryMapping.keySet().stream()
                .filter(each -> Pattern.compile(each).matcher(entryRoute).find())
                .findFirst();
        return entryMapping.isPresent() ? Optional.of(mEntryMapping.get(entryMapping.get())) : Optional.empty();
    }

    public Object invoke(Request request, Response response) throws MethodInvokeException, RequestMappingNotFoundException {
        try {
            Optional<Entry> matchedEntry = matchEntry(request.getRequestRoute());
            Preconditions.when(!matchedEntry.isPresent()).throwException(RequestMappingNotFoundException.class, request.getRequestRoute());

            request.appendParameters(matchedEntry.get().parseRouteParam(request.getRequestRoute()));
            return matchedEntry.get().invoke(mRouterInstance, request, response);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new MethodInvokeException(e);
        }
    }
}
