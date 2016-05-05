package miuyongjun.twentysix.ui.video;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface VideoFullScreenContract {
    interface View extends BaseView<Presenter> {

        void showLoadErrorData();
    }
    interface Presenter extends BasePresenter {
        void loadData();
    }
}
