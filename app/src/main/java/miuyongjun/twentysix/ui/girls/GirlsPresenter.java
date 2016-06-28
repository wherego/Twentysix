package miuyongjun.twentysix.ui.girls;

import javax.inject.Inject;

import miuyongjun.twentysix.common.Constant;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GirlsPresenter implements GirlsContract.Presenter {

    private final GirlsContract.View mGirlsView;
    private CompositeSubscription mSubscriptions;

    @Inject
    public GirlsPresenter(GirlsContract.View gankView) {
        this.mGirlsView = gankView;
        mSubscriptions = new CompositeSubscription();
        mGirlsView.setPresenter(this);
    }


    @Override
    public void loadData(int pageIndex) {
        mSubscriptions.clear();
        Subscription subscription = GirlsRepository.getInstance().getGankData(pageIndex)
                .subscribe(gankEntities -> {
                    if (gankEntities == null) {
                        mGirlsView.showLoadErrorData();
                        return;
                    } else if (gankEntities.size() < Constant.PAGE_SIZE) {
                        mGirlsView.showNoData();
                    }
                    mGirlsView.showData(gankEntities);
                }, throwable -> mGirlsView.showLoadErrorData(), mGirlsView::showCompletedData);
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
