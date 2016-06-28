package miuyongjun.twentysix.di.components;

import dagger.Component;
import miuyongjun.twentysix.di.modules.TopicPresenterModule;
import miuyongjun.twentysix.di.scopes.FragmentScoped;
import miuyongjun.twentysix.ui.specialtopic.TopicPresenter;

/**
 * Created by miaoyongjun on 16/5/10.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

@FragmentScoped
@Component(modules = TopicPresenterModule.class)
public interface TopicComponent {
    TopicPresenter getTopicPresenter();
}
