package priv.taaang.magi.exception;

/**
 * Created by TdaQ on 2016/12/29.
 */
public class MagiRestDefaultException extends RuntimeException{
    public MagiRestDefaultException(Exception e) {
        super(e.getMessage());
        this.addSuppressed(e);
    }

    public MagiRestDefaultException(String message) {
        super(message);
    }
}
