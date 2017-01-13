package priv.taaang.magi.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by TdaQ on 2016/12/30.
 */
class JSONParser implements Parser{

    private ObjectMapper mObjectMapper = new ObjectMapper();

    public JSONParser() {
        mObjectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> parse(InputStream inputStream) throws IOException{
        return mObjectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>(){});
    }

    @Override
    public String generate(Object object) throws IOException {
        return mObjectMapper.writeValueAsString(object);
    }
}
