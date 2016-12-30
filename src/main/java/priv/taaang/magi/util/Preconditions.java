package priv.taaang.magi.util;

import priv.taaang.magi.exception.MagiRestDefaultException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by TdaQ on 2016/12/29.
 */
public class Preconditions {

    public static class Builder {
        private boolean mExpression;
        private Exception mException;

        Builder(boolean expression) {
            mExpression = expression;
        }

        public void throwException(Class<? extends Exception> exceptionClazz, String errorMessage) throws Exception {
            if (mExpression) {
                try {
                    mException = exceptionClazz.getDeclaredConstructor(String.class).newInstance(errorMessage);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    mException = new MagiRestDefaultException(errorMessage);
                }
                throw mException;
            }
        }

        public void throwException(Class<? extends Exception> exceptionClazz) throws Exception {
            throwException(exceptionClazz, String);
        }
    }

    public static Builder when(boolean expression) {
        return new Builder(expression);
    }

}
