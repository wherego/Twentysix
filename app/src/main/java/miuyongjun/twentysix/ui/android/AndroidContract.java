package miuyongjun.twentysix.ui.android;

import java.util.List;

import miuyongjun.twentysix.BasePresenter;
import miuyongjun.twentysix.BaseView;
import miuyongjun.twentysix.bean.bmob.Article;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface AndroidContract {
    interface View extends BaseView<Presenter> {
        void showNoData();

        void showData(List<Article> articleEntities);

        void showCompletedData();

        void showLoadErrorData();
    }
    interface Presenter extends BasePresenter {
        void loadData(String topicId,int pageIndex);
    }
}
