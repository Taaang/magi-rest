package priv.taaang.magi.filter;

import priv.taaang.magi.parser.BodyParser;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.response.Response;

import java.util.Map;

/**
 * Created by TdaQ on 2017/1/14.
 */
public class JSONParseFilter implements Filter {
    @Override
    public void before(Request request, Response response) {
        Map<String, Object> bodyParameters = BodyParser.parse(request);
        request.appendParameters(bodyParameters);
    }

    @Override
    public void after(Request request, Response response) {

    }

    @Override
    public void complete(Request request, Response response) {

    }
}
