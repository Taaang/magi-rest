package priv.taaang.magi.request;

import priv.taaang.magi.route.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TdaQ on 16/11/15.
 */
public class ServletRequest implements Request {

    private HttpServletRequest mRawRequest;

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
}
