package miuyongjun.twentysix.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.utils.CrashHandler;

/**
 * Created by miaoyongjun on 2016/6/23.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class ApplicationModule {
    Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public CrashHandler provideCrashHandler() {
        return new CrashHandler();
    }
}
