package miuyongjun.twentysix.api;

import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by miaoyongjun on 2016/4/30.
 *    　　　　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public interface GankApi {
    @GET("Android/{pageSize}/{pageIndex}")
    Observable<GankBaseEntity> getGankData(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);
}
