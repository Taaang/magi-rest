package priv.taaang.magi.filter;

import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;
import priv.taaang.magi.route.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by TdaQ on 2017/1/13.
 */
public class FilterChain {

    private List<Filter> mGlobalFilterChain = new ArrayList<>();

    public void registerGlobalFilter(Filter filter) {
        mGlobalFilterChain.add(filter);
    }

    public void invokeFilterBefore(Request request, Response response, Router router) {
        if (router != null) {
            mGlobalFilterChain.stream().forEach(filter -> filter.before(request, response));
        }
    }

    public void invokeFilterAfter(Request request, Response response, Router router) {
        if (router != null) {
            mGlobalFilterChain.stream().forEach(filter -> filter.after(request, response));
        }
    }

    public void invokeFilterComplete(Request request, Response response, Router router) {
        if (router != null) {
            mGlobalFilterChain.stream().forEach(filter -> filter.complete(request, response));
        }
    }
}
