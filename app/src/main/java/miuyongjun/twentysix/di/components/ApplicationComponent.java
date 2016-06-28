package miuyongjun.twentysix.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import miuyongjun.twentysix.di.modules.ApplicationModule;
import miuyongjun.twentysix.utils.CrashHandler;

/**
 * Created by miaoyongjun on 2016/6/23.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();

    CrashHandler getCrashHandler();
}
