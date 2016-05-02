package miuyongjun.twentysix;

import android.app.Application;
import android.content.Context;

import miuyongjun.twentysix.utils.ToastUtils;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class TwentySixApplication extends Application{
    public static Context sContext;


    @Override public void onCreate() {
        super.onCreate();
        sContext = this;
        ToastUtils.register(this);
    }


    @Override public void onTerminate() {
        super.onTerminate();
    }
}
