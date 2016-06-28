package miuyongjun.twentysix.di.components;

import dagger.Component;
import miuyongjun.twentysix.ui.gankdetail.GankDetailPresenter;
import miuyongjun.twentysix.di.modules.GankDetailPresenterModule;
import miuyongjun.twentysix.di.scopes.FragmentScoped;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

@FragmentScoped
@Component(modules = GankDetailPresenterModule.class)
public interface GankDetailComponent {
    GankDetailPresenter getGankDetailPresenter();
}
