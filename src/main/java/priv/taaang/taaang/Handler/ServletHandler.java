package priv.taaang.taaang.handler;

import priv.taaang.taaang.request.ServletRequest;
import priv.taaang.taaang.response.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TdaQ on 16/11/15.
 */
public class ServletHandler implements Handler{

    private static class ServletHandlerHolder {
        private static final ServletHandler instance = new ServletHandler();
    }

    private ServletHandler() {}

    static ServletHandler getInstance() {
        return ServletHandlerHolder.instance;
    }

    public void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        ServletRequest request = new ServletRequest(servletRequest);
        ServletResponse response = new ServletResponse(servletResponse);

    }
}
