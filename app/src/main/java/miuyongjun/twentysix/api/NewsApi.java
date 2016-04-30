package miuyongjun.twentysix.api;

import miuyongjun.twentysix.bean.news.NewsBaseEntity;
import miuyongjun.twentysix.common.Constant;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/30.
 */

public interface NewsApi {
    @Headers("apikey:" + Constant.NEWS_API_KEY)
    @GET("http://apis.baidu.com/cd_boco/chinanews/testnewsapi")
    Observable<NewsBaseEntity> getNewsData(@Query("query") String json);
}
