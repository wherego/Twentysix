package miuyongjun.twentysix.ui.android;

import android.content.Context;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import miuyongjun.twentysix.bean.bmob.Article;
import miuyongjun.twentysix.common.Constant;
import rx.Observable;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidRepository {

    private static AndroidRepository INSTANCE = null;

    public AndroidRepository() {

    }

    public static AndroidRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AndroidRepository();
        }
        return INSTANCE;
    }


    public Observable<List<Article>> getArticleData(Context context, String topicId, int pageIndex) {
        return Observable.create((Observable.OnSubscribe<List<Article>>) subscriber -> {
            BmobQuery<Article> bmobQuery = new BmobQuery<>();
            if (topicId != null) bmobQuery.addWhereEqualTo("topicId", topicId);
            bmobQuery.setSkip(Constant.PAGE_SIZE * (pageIndex - 1));
            bmobQuery.setLimit(Constant.PAGE_SIZE * pageIndex);
            bmobQuery.order("-createdAt");
            bmobQuery.findObjects(context, new FindListener<Article>() {
                @Override
                public void onSuccess(List<Article> list) {
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                }

                @Override
                public void onError(int i, String s) {
                    subscriber.onError(new Throwable("errorCode:" + i + "errorMessage:" + s));
                }
            });
        });
    }
}
