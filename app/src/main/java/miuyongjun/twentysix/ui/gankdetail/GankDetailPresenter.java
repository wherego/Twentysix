package miuyongjun.twentysix.ui.gankdetail;

import java.util.Calendar;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDetailPresenter implements GankDetailContract.Presenter {

    private final GankDetailContract.View mGankView;
    private CompositeSubscription mSubscriptions;

    @Inject
    public GankDetailPresenter(GankDetailContract.View gankView) {
        this.mGankView = gankView;
        mSubscriptions = new CompositeSubscription();
        mGankView.setPresenter(this);
    }


    @Override
    public void loadData(Calendar calendar) {
        mSubscriptions.clear();
        Subscription subscription = GankDetailRepository.getInstance().getGankData(calendar)
                .subscribe(mGankView::showData,
                        throwable -> mGankView.showLoadErrorData(),
                        mGankView::showCompletedData);
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
