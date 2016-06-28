package miuyongjun.twentysix.di.modules;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.gankdetail.GankDetailContract;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class GankDetailPresenterModule {
    private final GankDetailContract.View mView;

    public GankDetailPresenterModule(GankDetailContract.View view) {
        mView = view;
    }

    @Provides @FragmentScoped
    GankDetailContract.View provideGankDetailContractView() {
        return mView;
    }
}
