package priv.taaang.taaang.handler;

import java.util.function.Supplier;

/**
 * Created by TdaQ on 16/12/12.
 */
public enum HandlerConstructor {
    SERVLET_HANDLER(ServletHandler::getInstance);

    private Supplier<Handler> mConstructor;

    HandlerConstructor(Supplier<Handler> mHandlerClass) {
        this.mConstructor = mHandlerClass;
    }

    public Handler create() {
        return mConstructor.get();
    }
}
