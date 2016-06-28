package miuyongjun.twentysix.ui.girls;

import java.util.List;

import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import rx.Observable;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GirlsRepository {

    private static GirlsRepository INSTANCE = null;

    public GirlsRepository() {

    }

    public static GirlsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GirlsRepository();
        }
        return INSTANCE;
    }


    public Observable<List<GankEntity>> getGankData(int pageIndex) {
        return RetrofitUtil.getGirlsApi(Constant.GANK_FULI, pageIndex).
                flatMap(gankBaseEntity -> Observable.from(gankBaseEntity.gankEntityList).toList());
    }
}
