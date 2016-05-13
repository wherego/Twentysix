package miuyongjun.twentysix.ui.gankdetail;

import dagger.Component;
import miuyongjun.twentysix.utils.FragmentScoped;

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
