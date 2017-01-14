package priv.taaang.magi.filter;

import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;

/**
 * Created by TdaQ on 2017/1/13.
 */
public interface Filter {

    public void before(Request request, Response response);

    public void after(Request request, Response response);

    public void complete(Request request, Response response);
}
