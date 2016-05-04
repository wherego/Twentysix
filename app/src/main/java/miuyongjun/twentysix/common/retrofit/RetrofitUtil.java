package miuyongjun.twentysix.common.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import miuyongjun.twentysix.api.GankApi;
import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import miuyongjun.twentysix.bean.gank.GankDateBaseEntity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　   　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class RetrofitUtil {
    static GankApi gankApi;

    public static Retrofit RetrofitSetting() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder()
                .client(client)
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
