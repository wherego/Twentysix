package miuyongjun.twentysix.di.modules;

import dagger.Module;
import dagger.Provides;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.specialtopic.TopicContract;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@Module
public class TopicPresenterModule {
    private final TopicContract.View mView;

    public TopicPresenterModule(TopicContract.View view) {
        mView = view;
    }

    @Provides @FragmentScoped
    TopicContract.View provideGirlsContractView() {
        return mView;
    }
}
