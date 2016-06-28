package miuyongjun.twentysix.di.components;

import dagger.Component;
import miuyongjun.twentysix.ui.gank.GankPresenter;
import miuyongjun.twentysix.di.modules.GankPresenterModule;
import miuyongjun.twentysix.di.scopes.FragmentScoped;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

@FragmentScoped
@Component(modules = GankPresenterModule.class)
public interface GankComponent {
    GankPresenter getGankPresenter();
}
