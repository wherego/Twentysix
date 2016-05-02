package miuyongjun.twentysix.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import miuyongjun.twentysix.api.GankApi;
import miuyongjun.twentysix.api.NewsApi;
import miuyongjun.twentysix.api.WXHotApi;
import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import miuyongjun.twentysix.bean.gank.GankDateBaseEntity;
import miuyongjun.twentysix.bean.news.NewsBaseEntity;
import miuyongjun.twentysix.bean.wechat.WXHotBaseEntity;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　   　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class RetrofitUtil {
    static NewsApi newsApi;
    static GankApi gankApi;
    static WXHotApi wxHotApi;

    public static Retrofit RetrofitSetting() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://gank.io/api/data/")
                .build();
    }

    /**
     * 使用Transformer封装开启线程的重用方法
     */

    static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<NewsBaseEntity> getNewsApi(String json) {
        if (newsApi == null) {
            newsApi = RetrofitSetting().create(NewsApi.class);
        }
        return newsApi.getNewsData(json).compose(RetrofitUtil.<NewsBaseEntity>applySchedulers());
    }

    public static Observable<WXHotBaseEntity> getWXHotApi(int page) {
        if (wxHotApi == null) {
            wxHotApi = RetrofitSetting().create(WXHotApi.class);
        }
        return wxHotApi.getWXHotData("10", "1", page + "").compose(RetrofitUtil.<WXHotBaseEntity>applySchedulers());
    }

    public static Observable<GankBaseEntity> getGirlsApi(String type, int pageIndex) {
        if (gankApi == null) {
            gankApi = RetrofitSetting().create(GankApi.class);
        }
        return gankApi.getGankData(type, pageIndex).compose(RetrofitUtil.<GankBaseEntity>applySchedulers());
    }

    public static Observable<GankDateBaseEntity> getGankBaseApi(int year, int month, int day) {
        if (gankApi == null) {
            gankApi = RetrofitSetting().create(GankApi.class);
        }
        return gankApi.getGankDateData(year, month, day).compose(RetrofitUtil.<GankDateBaseEntity>applySchedulers());
    }

    public static Observable<GankBaseEntity> getGankApi(String type, int pageIndex) {
        if (gankApi == null) {
            gankApi = RetrofitSetting().create(GankApi.class);
        }
        return gankApi.getGankData(type, pageIndex).compose(RetrofitUtil.<GankBaseEntity>applySchedulers());
    }


}
