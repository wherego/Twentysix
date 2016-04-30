package miuyongjun.twentysix.utils;


public class Log {
    private Log() {
    }

    /**
     * 调试模式，打正式包改为false
     */
    public final static boolean debug = true;
    /**
     * tag
     */
    public static final String TAG = "Community";

    public static void i(String msg) {
        if (debug)
            android.util.Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (debug)
            android.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (debug)
            android.util.Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (debug)
            android.util.Log.v(TAG, msg);
    }

    public static void throwable(Throwable e) {
        if (debug)
            e.printStackTrace();
    }
}
