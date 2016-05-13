package miuyongjun.twentysix.ui.gankdetail;

import java.util.Calendar;

import miuyongjun.twentysix.bean.gank.GankDateEntity;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDetailRepository {

    private static GankDetailRepository INSTANCE = null;

    public GankDetailRepository() {

    }

    public static GankDetailRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GankDetailRepository();
        }
        return INSTANCE;
    }


    public Observable<GankDateEntity> getGankData(Calendar calendar) {
        return RetrofitUtil.getGankBaseApi(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH))
                .flatMap(gankDateBaseEntity -> Observable.create(new Observable.OnSubscribe<GankDateEntity>() {
                    @Override
                    public void call(Subscriber<? super GankDateEntity> subscriber) {
                        if (gankDateBaseEntity.results == null)
                            subscriber.onError(new Throwable("no data return"));
                        subscriber.onNext(gankDateBaseEntity.results);
                        subscriber.onCompleted();
                    }
                }));
    }
}
