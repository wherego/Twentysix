package miuyongjun.twentysix.ui.gankdetail;

import java.util.Calendar;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;
import miuyongjun.twentysix.bean.gank.GankDateEntity;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface GankDetailContract {
    interface View extends BaseView<Presenter> {
        void showNoData();

        void showData(GankDateEntity gankDateEntity);

        void showCompletedData();

        void showLoadErrorData();

    }
    interface Presenter extends BasePresenter {
        void loadData(Calendar calendar);
    }
}
