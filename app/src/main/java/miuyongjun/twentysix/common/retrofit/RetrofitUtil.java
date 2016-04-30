package miuyongjun.twentysix.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import miuyongjun.twentysix.api.GankApi;
import miuyongjun.twentysix.api.NewsApi;
import miuyongjun.twentysix.bean.news.NewsBaseEntity;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miaoyongjun on 2016/4/30.
 *    　　　　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class RetrofitUtil {
    static NewsApi newsApi;
    static GankApi gankApi;

    public static Retrofit RetrofitSetting() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://gank.io/api/data/")
                .build();
        return retrofit;
    }

    /**
     * 使用Transformer封装开启线程的重用方法
     * */
    final static Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    public static Observable<NewsBaseEntity> getNewsApi(String json) {
        if (newsApi == null) {
            newsApi = RetrofitSetting().create(NewsApi.class);
        }
        return newsApi.getNewsData(json).compose(RetrofitUtil.<NewsBaseEntity>applySchedulers());
    }

    public static GankApi getGankApi() {
        if (gankApi == null) {
            gankApi = RetrofitSetting().create(GankApi.class);
        }
        return gankApi;
    }
}
