package priv.taaang.magi.parser;

import priv.taaang.magi.exception.BodyParseException;
import priv.taaang.magi.exception.BodyParserNotFoundException;
import priv.taaang.magi.request.Request;
import priv.taaang.magi.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by TdaQ on 2016/12/30.
 */
public class BodyParser {

    private static final Map<String, Parser> mParserMapping = new HashMap<>();

    static {
        mParserMapping.put(BodyParserType.APPLICATION_JSON.value(), new JSONParser());
    }

    public enum BodyParserType {
        APPLICATION_JSON("application/json");

        private String mContentType;

        BodyParserType(String contentType) {
            mContentType = contentType;
        }
        String value() {
            return mContentType;
        }
    }

    public static Map<String, Object> parse(Request request) throws BodyParseException {
        try {
            Optional<String> parserName = mParserMapping.keySet().stream().filter(each -> request.getContentType().contains(each)).findFirst();
            Preconditions.when(!parserName.isPresent()).throwException(BodyParserNotFoundException.class, request.getContentType());
            return mParserMapping.get(parserName.get()).parse(request.getRawRequest().getInputStream());
        } catch (IOException | BodyParserNotFoundException e) {
            throw new BodyParseException(e);
        }
    }

    public static String generate(BodyParserType bodyParserType, Object object) throws IOException {
        return mParserMapping.get(bodyParserType.value()).generate(object);
    }
}
