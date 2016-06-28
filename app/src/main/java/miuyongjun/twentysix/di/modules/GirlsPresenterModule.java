package miuyongjun.twentysix.di.modules;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.girls.GirlsContract;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class GirlsPresenterModule {
    private final GirlsContract.View mView;

    public GirlsPresenterModule(GirlsContract.View view) {
        mView = view;
    }

    @Provides @FragmentScoped
    GirlsContract.View provideGirlsContractView() {
        return mView;
    }
}
