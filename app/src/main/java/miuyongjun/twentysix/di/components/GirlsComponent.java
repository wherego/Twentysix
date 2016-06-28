package miuyongjun.twentysix.di.components;

import dagger.Component;
import miuyongjun.twentysix.di.modules.GirlsPresenterModule;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.girls.GirlsPresenter;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

@FragmentScoped
@Component(modules = GirlsPresenterModule.class)
public interface GirlsComponent {
    GirlsPresenter getGirlsPresenter();
}
