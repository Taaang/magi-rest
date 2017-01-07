package priv.taaang.magi.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by TdaQ on 16/11/15.
 */
public interface Request {

    String getServletPath();

    String getRequestMethod();

    String getRequestRoute();

    String getContentType();

    HttpServletRequest getRawRequest();

    void setParameters(Map<String, Object> parameters);

    void appendParameters(Map<String, Object> toAppend);

    Map<String, Object> getParameters();

}
