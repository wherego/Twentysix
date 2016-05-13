package miuyongjun.twentysix.ui.gank;

import java.util.List;

import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import rx.Observable;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankRepository {

    private static GankRepository INSTANCE = null;

    public GankRepository() {

    }

    public static GankRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GankRepository();
        }
        return INSTANCE;
    }


    public Observable<List<GankEntity>> getGankData(String type, int pageIndex) {
        return RetrofitUtil.getGankApi(type, pageIndex)
                .flatMap(gankBaseEntity -> Observable.from(gankBaseEntity.gankEntityList).toList());
    }
}
