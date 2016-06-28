package miuyongjun.twentysix.ui.specialtopic;

import java.util.List;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;
import miuyongjun.twentysix.bean.bmob.SpecialTopic;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface TopicContract {
    interface View extends BaseView<Presenter> {

        void showData(List<SpecialTopic> spHotEntities);

        void showNoData();

        void showCompletedData();

        void showLoadErrorData();

    }
    interface Presenter extends BasePresenter {
        void loadData(int pageIndex);
    }
}
