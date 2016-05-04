package miuyongjun.twentysix.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import miuyongjun.twentysix.common.interfaces.ReTryCallBack;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class ToastUtils {
    public static Context sContext;


    private ToastUtils() {
    }


    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }


    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtils.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }

    public static void showSnakbar(int showTitle, View view) {
        Snackbar.make(view, showTitle, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnakbarErrorWithCallBack(Throwable throwable, View view, ReTryCallBack reTryCallBack) {
        throwable.printStackTrace();
        Snackbar.make(view, "加载失败，请重试",
                Snackbar.LENGTH_LONG).setAction("重试", v -> {
            reTryCallBack.reTry();
        }).show();
    }


    public static void showShort(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }
}
