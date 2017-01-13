package priv.taaang.magi.handler;

import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;

/**
 * Created by TdaQ on 2017/1/12.
 */
public interface ExceptionHandler {

    public Object handle(Request request, Response response, Exception exception);

}
