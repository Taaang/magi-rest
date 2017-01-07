package priv.taaang.magi.exception;

/**
 * Created by TdaQ on 2017/1/6.
 */
public class BodyParserNotFoundException extends Exception {
    public BodyParserNotFoundException(Exception e) {
        super(e.getMessage());
        this.addSuppressed(e);
    }
}
