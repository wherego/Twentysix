package miuyongjun.twentysix.ui.gank;

import java.util.List;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;
import miuyongjun.twentysix.bean.gank.GankEntity;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface GankContract {
    interface View extends BaseView<Presenter> {
        void showNoData();

        void showData(List<GankEntity> gankEntities);

        void showCompletedData();

        void showLoadErrorData();
    }
    interface Presenter extends BasePresenter {
        void loadData(String topicId, int pageIndex);
    }
}
