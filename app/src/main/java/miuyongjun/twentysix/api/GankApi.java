package miuyongjun.twentysix.api;

import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import miuyongjun.twentysix.bean.gank.GankDateBaseEntity;
import miuyongjun.twentysix.common.Constant;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public interface GankApi {

    @GET(Constant.GANK_SERVER_IP + "/data/{type}/" + Constant.PAGE_SIZE + "/{pageIndex}")
    Observable<GankBaseEntity> getGankData(@Path("type") String type, @Path("pageIndex") int pageIndex);

    @GET(Constant.GANK_SERVER_IP + "/day/{year}/{month}/{day}")
    Observable<GankDateBaseEntity> getGankDateData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

//    @GET("/data/休息视频/" + Constant.PAGE_SIZE + "/{page}") Observable<休息视频Data> get休息视频Data(
//            @Path("page") int page);

}
