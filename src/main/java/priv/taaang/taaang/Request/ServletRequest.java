package priv.taaang.taaang.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TdaQ on 16/11/15.
 */
public class ServletRequest implements Request {

    private HttpServletRequest mRawRequest;

    public ServletRequest(HttpServletRequest servletRequest) {
        mRawRequest = servletRequest;
    }
}
