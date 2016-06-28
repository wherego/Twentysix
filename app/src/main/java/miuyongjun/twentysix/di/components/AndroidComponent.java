package miuyongjun.twentysix.di.components;

import dagger.Component;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.android.AndroidPresenter;
import miuyongjun.twentysix.di.modules.AndroidPresenterModule;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

@FragmentScoped
@Component(modules = AndroidPresenterModule.class)
public interface AndroidComponent {
    AndroidPresenter getAndroidPresenter();
}
