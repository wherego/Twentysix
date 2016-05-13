package miuyongjun.twentysix.ui.gankdetail;

import dagger.Module;
import dagger.Provides;

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

    @Provides
    GankDetailContract.View provideGankDetailContractView() {
        return mView;
    }
}
