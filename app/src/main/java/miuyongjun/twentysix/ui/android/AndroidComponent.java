package miuyongjun.twentysix.ui.android;

import dagger.Component;
import miuyongjun.twentysix.utils.FragmentScoped;

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
