package miuyongjun.twentysix.di.modules;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.gank.GankContract;

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

    @Provides @FragmentScoped
    GankContract.View provideAndroidContractView() {
        return mView;
    }
}
