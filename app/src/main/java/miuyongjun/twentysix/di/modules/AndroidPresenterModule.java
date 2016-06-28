package miuyongjun.twentysix.di.modules;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.ui.android.AndroidContract;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class AndroidPresenterModule {
    private final AndroidContract.View mView;

    public AndroidPresenterModule(AndroidContract.View view) {
        mView = view;
    }

    @Provides
    AndroidContract.View provideAndroidContractView() {
        return mView;
    }
}
