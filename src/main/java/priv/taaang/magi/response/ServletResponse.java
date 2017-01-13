package priv.taaang.magi.response;

import priv.taaang.magi.exception.ResponseErrorException;
import priv.taaang.magi.parser.BodyParser;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Created by TdaQ on 16/12/12.
 */
public class ServletResponse implements Response{

    private HttpServletResponse mRawResponse;

    private static final int RESPONSE_OUTPUT_PART_SIZE = 8 * 1024;

    public ServletResponse(HttpServletResponse response) {
        mRawResponse = response;
    }

    @Override
    public void respond(Object body) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(mRawResponse.getOutputStream(), RESPONSE_OUTPUT_PART_SIZE)) {
            String responseBody = BodyParser.generate(BodyParser.BodyParserType.APPLICATION_JSON, body);
            outputStream.write(responseBody.getBytes(), 0, responseBody.length());
            outputStream.flush();
        } catch (IOException e) {
            throw new ResponseErrorException(e);
        }
    }
}
