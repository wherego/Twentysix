package miuyongjun.twentysix.ui.android;

import dagger.Module;
import dagger.Provides;

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
