package priv.taaang.magi.route;

import priv.taaang.magi.exception.RequestMappingNotFoundException;
import priv.taaang.magi.route.label.RequestMapping;
import priv.taaang.magi.route.label.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
    private List<String> mParameterList = new ArrayList<>();
    private Method mMethod;

    private static final String REGEX_REPLACE_PATH_PARAM_LABEL = "\\{([a-zA-Z0-9]+)\\}";
    private static final Pattern mReplacePathParamPattern = Pattern.compile(REGEX_REPLACE_PATH_PARAM_LABEL);
    private static final String REGEX_PATH_PARAM_EXTRACTOR = "([a-zA-Z0-9]+)";
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
        while (matcher.find()) {
            for (int index = 1; index <= matcher.groupCount(); ++index) {
                mParameterList.add(matcher.group(index));
            }
        }

        mMethod = method;
        mEntryMethod = requestMapping.method();
        mEntryRoute = EntryBuilder.buildRegex(mEntryMethod.value(), matcher.replaceAll(REGEX_PATH_PARAM_EXTRACTOR));
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
        return mParameterList;
    }

    public Map<String, Object> matchEntryParameter() {
        return null;
    }
}
