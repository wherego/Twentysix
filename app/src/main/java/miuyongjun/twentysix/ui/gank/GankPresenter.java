package miuyongjun.twentysix.ui.gank;

import javax.inject.Inject;

import miuyongjun.twentysix.common.Constant;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankPresenter implements GankContract.Presenter {

    private final GankContract.View mGankView;
    private CompositeSubscription mSubscriptions;

    @Inject
    public GankPresenter(GankContract.View mGankView) {
        this.mGankView = mGankView;
        mSubscriptions = new CompositeSubscription();
        mGankView.setPresenter(this);
    }


    @Override
    public void loadData(String type, int pageIndex) {
        mSubscriptions.clear();
        Subscription subscription = GankRepository.getInstance().getGankData(type, pageIndex)
                .subscribe(gankEntities -> {
                    if (gankEntities == null) {
                        mGankView.showLoadErrorData();
                        return;
                    } else if (gankEntities.size() < Constant.PAGE_SIZE) {
                        mGankView.showNoData();
                    }
                    mGankView.showData(gankEntities);
                }, throwable -> mGankView.showLoadErrorData(), mGankView::showCompletedData);
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
