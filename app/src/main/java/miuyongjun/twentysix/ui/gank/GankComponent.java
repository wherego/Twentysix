package miuyongjun.twentysix.ui.gank;

import dagger.Component;
import miuyongjun.twentysix.utils.FragmentScoped;

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
