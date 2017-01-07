package priv.taaang.magi.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by TdaQ on 2016/12/30.
 */
class JSONParser implements Parser{

    @Override
    public Map<String, Object> parse(InputStream inputStream) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>(){});
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String content;
        StringBuilder result = new StringBuilder();
        while ((content = bufferedReader.readLine()) != null) {
            result.append(content);
        }
        return result.toString();
    }
}
