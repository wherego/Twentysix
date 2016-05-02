package miuyongjun.twentysix.api;

import miuyongjun.twentysix.bean.wechat.WXHotBaseEntity;
import miuyongjun.twentysix.common.Constant;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public interface WXHotApi {
    /**
     * 由于API的要求 所以必须要写多个方法
     */
    @Headers("apikey:" + Constant.NEWS_API_KEY)
    @GET("http://apis.baidu.com/txapi/weixin/wxhot")
    /**
     * @param num
     * 文章返回数量
     * @param rand
     * 是否随机，1表示随机
     * @param word
     * 检索关键字
     * @param page
     * 翻页，输出数量跟随num参数
     * @param src
     * 指定文章来源
     * */
    Observable<WXHotBaseEntity> getWXHotData(
            @Query("num") String num, @Query("rand") String rand, @Query("page") String page);

    Observable<WXHotBaseEntity> getWXHotSearchData(
            @Query("num") String num, @Query("rand") String rand,
            @Query("word") String word, @Query("page") String page);
}
