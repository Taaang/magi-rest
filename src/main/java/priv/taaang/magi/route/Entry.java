package priv.taaang.magi.route;

import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;
import priv.taaang.magi.route.label.RequestMapping;
import priv.taaang.magi.route.label.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TdaQ on 16/12/16.
 */
public class Entry {

    private String mEntryRoute;
    private RequestMethod mEntryMethod;
    private Pattern mEntryRoutePattern;
    private List<String> mPathParameters = new ArrayList<>();
    private Method mMethod;

    private static final String REGEX_GROUP_PARAMETER = "parameter";
    private static final String REGEX_REPLACE_PATH_PARAM_LABEL = "\\{(?<"+ REGEX_GROUP_PARAMETER + ">[a-zA-Z0-9]+)\\}";
    private static final Pattern mReplacePathParamPattern = Pattern.compile(REGEX_REPLACE_PATH_PARAM_LABEL);
    private static final String REGEX_PATH_PARAM_EXTRACTOR = "(?<%s>\\\\w+)";
    private static final String DEFAULT_ENTRY_ROUTE_FORMAT = "[%s]%s";
    private static final String DEFAULT_ENTRY_ROUTE_REGEX_FORMAT = "\\[%s\\]%s";

    Entry(Method method) {
        buildFromMethod(method);
    }

    private void buildFromMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            throw new RequestMappingNotFoundException();
        }

        Matcher matcher = mReplacePathParamPattern.matcher(requestMapping.route());
        StringBuffer regexReplaceResult = new StringBuffer();
        while (matcher.find()) {
            String parameter = matcher.group(REGEX_GROUP_PARAMETER);
            mPathParameters.add(parameter);
            matcher.appendReplacement(regexReplaceResult, String.format(REGEX_PATH_PARAM_EXTRACTOR, parameter));
        }
        matcher.appendTail(regexReplaceResult);

        mMethod = method;
        mEntryMethod = requestMapping.method();
        mEntryRoute = EntryBuilder.buildRegex(mEntryMethod.value(), regexReplaceResult.toString());
        mEntryRoutePattern = Pattern.compile(regexReplaceResult.toString());
    }

    public Map<String, Object> parseRouteParam(String requestRoute) {
        Matcher matcher = mEntryRoutePattern.matcher(requestRoute);
        Map<String, Object> parameters = new HashMap<>();;
        if (matcher.find()) {
            for (String each : mPathParameters) {
                parameters.put(each, matcher.group(each));
            }
        }
        return parameters;
    }

    public Object invoke(Object caller, Request request, Response response) throws InvocationTargetException, IllegalAccessException{
        return mMethod.invoke(caller, request, response);
    }

    public static class EntryBuilder {

        public static String buildRoute(String method, String requestURI) {
            return String.format(DEFAULT_ENTRY_ROUTE_FORMAT, method, requestURI);
        }
        static String buildRegex(String method, String requestURI) {
            return String.format(DEFAULT_ENTRY_ROUTE_REGEX_FORMAT, method, requestURI);
        }
    }

    public String getEntryRoute() {
        return mEntryRoute;
    }

    public List<String> getParameterList() {
        return mPathParameters;
    }

    public Map<String, Object> matchEntryParameter() {
        return null;
    }
}
