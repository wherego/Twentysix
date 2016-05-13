package miuyongjun.twentysix.ui.gank;

import dagger.Module;
import dagger.Provides;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class GankPresenterModule {
    private final GankContract.View mView;

    public GankPresenterModule(GankContract.View view) {
        mView = view;
    }

    @Provides
    GankContract.View provideAndroidContractView() {
        return mView;
    }
}
