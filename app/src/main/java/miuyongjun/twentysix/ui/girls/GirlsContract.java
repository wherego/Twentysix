package miuyongjun.twentysix.ui.girls;

import java.util.List;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;
import miuyongjun.twentysix.bean.gank.GankEntity;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface GirlsContract {
    interface View extends BaseView<Presenter> {
        void showNoData();

        void showData(List<GankEntity> gankEntities);

        void showCompletedData();

        void showLoadErrorData();

    }
    interface Presenter extends BasePresenter {
        void loadData(int pageIndex);
    }
}
