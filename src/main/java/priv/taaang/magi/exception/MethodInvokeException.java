package priv.taaang.magi.exception;

/**
 * Created by TdaQ on 2017/1/10.
 */
public class MethodInvokeException extends Exception{
    public MethodInvokeException(Exception e) {
        super(e.getMessage());
        this.addSuppressed(e);
    }
    public MethodInvokeException(String message) {
        super(message);
    }
}
