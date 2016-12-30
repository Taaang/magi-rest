package priv.taaang.magi.response;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by TdaQ on 16/12/12.
 */
public class ServletResponse implements Response{

    private HttpServletResponse mRawResponse;

    public ServletResponse(HttpServletResponse response) {
        mRawResponse = response;
    }
}
