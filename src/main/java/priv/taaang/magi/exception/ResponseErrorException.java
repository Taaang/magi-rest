package priv.taaang.magi.exception;

/**
 * Created by TdaQ on 2017/1/13.
 */
public class ResponseErrorException extends RuntimeException{
    public ResponseErrorException(Exception e) {
        super(e.getMessage());
        this.addSuppressed(e);
    }

    public ResponseErrorException(String errorMessage) {
        super(errorMessage);
    }
}
