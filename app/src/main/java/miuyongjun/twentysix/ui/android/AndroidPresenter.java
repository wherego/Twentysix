package miuyongjun.twentysix.ui.android;

import miuyongjun.twentysix.common.Constant;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidPresenter implements AndroidContract.Presenter {

    private final AndroidContract.View mAndroidView;
    private CompositeSubscription mSubscriptions;

    public AndroidPresenter(AndroidContract.View mAndroidView) {
        this.mAndroidView = mAndroidView;
        mSubscriptions = new CompositeSubscription();
        mAndroidView.setPresenter(this);
    }

    @Override
    public void loadData(String topicId, int pageIndex) {
        mSubscriptions.clear();
        Subscription subscription = AndroidRepository.getInstance().getArticleData(mAndroidView.getContext(), topicId, pageIndex)
                .subscribe(articles -> {
                    if (articles == null) {
                        mAndroidView.showLoadErrorData();
                        return;
                    } else if (articles.size() < Constant.PAGE_SIZE) {
                        mAndroidView.showNoData();
                    }
                    mAndroidView.showData(articles);
                }, throwable -> mAndroidView.showLoadErrorData(), mAndroidView::showCompletedData);
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
