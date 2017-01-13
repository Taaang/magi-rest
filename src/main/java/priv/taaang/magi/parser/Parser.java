package priv.taaang.magi.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by TdaQ on 2016/12/30.
 */
public interface Parser {

    public Map<String, Object> parse(InputStream inputStream) throws IOException;

    public String generate(Object object) throws IOException;
}
