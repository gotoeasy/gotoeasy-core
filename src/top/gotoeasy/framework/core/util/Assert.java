package top.gotoeasy.framework.core.util;

public class Assert {

    public static void notNull(Object obj, String msg) {
        if ( obj == null ) {
            throw new IllegalArgumentException(msg);
        }
    }

}
