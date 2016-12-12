package priv.taaang.taaang.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TdaQ on 16/11/4.
 */
public interface Handler {

    void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse);
}
