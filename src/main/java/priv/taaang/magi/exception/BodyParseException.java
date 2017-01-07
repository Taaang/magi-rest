package priv.taaang.magi.exception;

/**
 * Created by TdaQ on 2017/1/3.
 */
public class BodyParseException extends RuntimeException{
    public BodyParseException(Exception e) {
        super(e.getMessage());
        this.addSuppressed(e);
    }
}
