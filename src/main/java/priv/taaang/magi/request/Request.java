package priv.taaang.magi.request;

/**
 * Created by TdaQ on 16/11/15.
 */
public interface Request {

    String getServletPath();
    String getRequestMethod();

    String getRequestRoute();
}
