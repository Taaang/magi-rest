package priv.taaang.magi.request;

import priv.taaang.magi.route.Entry;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TdaQ on 16/11/15.
 */
public class ServletRequest implements Request {

    private HttpServletRequest mRawRequest;
    private Map<String, Object> mParameters = new HashMap<>();

    public ServletRequest(HttpServletRequest servletRequest) {
        mRawRequest = servletRequest;
    }

    @Override
    public String getServletPath() {
        return mRawRequest.getServletPath();
    }

    @Override
    public String getRequestMethod() {
        return mRawRequest.getMethod();
    }

    @Override
    public String getRequestRoute() {
        return Entry.EntryBuilder.buildRoute(mRawRequest.getMethod().toUpperCase(), mRawRequest.getServletPath());
    }

    @Override
    public HttpServletRequest getRawRequest() {
        return mRawRequest;
    }

    @Override
    public String getContentType() {
        return mRawRequest.getContentType();
    }

    @Override
    public Map<String, Object> getRawRequestPathParameters() {
        Map<String, String[]> pathParameters = mRawRequest.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        for (String each : pathParameters.keySet()) {
            String[] value = pathParameters.get(each);
            Object data = (value.length == 1) ? value[0] : new ArrayList<>(Arrays.asList(value));
            result.put(each, data);
        }
        return result;
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        mParameters.clear();
        mParameters.putAll(parameters);
    }

    @Override
    public void appendParameters(Map<String, Object> toAppend) {
        mParameters.putAll(toAppend);
    }

    @Override
    public Map<String, Object> getParameters() {
        return mParameters;
    }
}