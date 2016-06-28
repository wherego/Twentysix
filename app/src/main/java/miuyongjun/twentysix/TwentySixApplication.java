package miuyongjun.twentysix;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.di.components.ApplicationComponent;
import miuyongjun.twentysix.di.components.DaggerApplicationComponent;
import miuyongjun.twentysix.di.modules.ApplicationModule;
import miuyongjun.twentysix.utils.ToastUtils;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class TwentySixApplication extends Application {
    public static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getApplicationContext())).build();
        BmobConfig config = new BmobConfig.Builder()
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setBlockSize(500 * 1024)
                .build();
        Bmob.getInstance().initConfig(config);
        Bmob.initialize(this, Constant.BOMB_APPLICATION_ID);
        ToastUtils.register(this);
        applicationComponent.getCrashHandler().init(getApplicationContext());
        LeakCanary.install(this);
    }

    public static TwentySixApplication getApplication() {
        return (TwentySixApplication) applicationComponent.getContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
