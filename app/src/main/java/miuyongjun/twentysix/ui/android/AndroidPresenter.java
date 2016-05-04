package miuyongjun.twentysix.ui.android;

import java.util.List;

import miuyongjun.twentysix.bean.bmob.Article;
import miuyongjun.twentysix.common.Constant;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidPresenter implements AndroidContract.Presenter {

    private final AndroidContract.View mAndroidView;

    public AndroidPresenter(AndroidContract.View mAndroidView) {
        this.mAndroidView = mAndroidView;
        mAndroidView.setPresenter(this);
    }

    @Override
    public void loadData(int pageIndex) {
        AndroidRepository.getInstance().getArticleData(mAndroidView.getContext(), pageIndex, new LoadAndroidDataCallback() {
            @Override
            public void onAndroidDataLoaded(List<Article> articleList) {
                if (articleList == null) {
                    mAndroidView.showLoadErrorData();
                    return;
                } else if (articleList.size() < Constant.PAGE_SIZE) {
                    mAndroidView.showNoData();
                }
                mAndroidView.showData(articleList);
                mAndroidView.showCompletedData();
            }

            @Override
            public void onAndroidDataLoadError() {
                mAndroidView.showLoadErrorData();
            }
        });
    }

    @Override
    public void start() {

    }


}
