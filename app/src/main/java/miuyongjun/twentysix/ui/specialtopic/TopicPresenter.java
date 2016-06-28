package miuyongjun.twentysix.ui.specialtopic;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import miuyongjun.twentysix.bean.bmob.SpecialTopic;
import miuyongjun.twentysix.common.Constant;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class TopicPresenter implements TopicContract.Presenter {

    private final TopicContract.View mGirlsView;
    private CompositeSubscription mSubscriptions;

    @Inject
    public TopicPresenter(TopicContract.View gankView) {
        this.mGirlsView = gankView;
        mSubscriptions = new CompositeSubscription();
        mGirlsView.setPresenter(this);
    }


    @Override
    public void loadData(int pageIndex) {
        BmobQuery<SpecialTopic> bmobQuery = new BmobQuery<>();
        bmobQuery.setSkip(Constant.PAGE_SIZE * (pageIndex - 1));
        bmobQuery.setLimit(Constant.PAGE_SIZE * pageIndex);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(mGirlsView.getContext(), new FindListener<SpecialTopic>() {
            @Override
            public void onSuccess(List<SpecialTopic> list) {
                if (list == null) {
                    mGirlsView.showLoadErrorData();
                } else if (list.size() < Constant.PAGE_SIZE) {
                    mGirlsView.showNoData();
                }
            }

            @Override
            public void onError(int i, String s) {
                mGirlsView.showLoadErrorData();
            }
        });
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
