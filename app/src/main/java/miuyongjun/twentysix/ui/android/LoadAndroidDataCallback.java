package miuyongjun.twentysix.ui.android;

import java.util.List;

import miuyongjun.twentysix.bean.bmob.Article;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public interface LoadAndroidDataCallback {

        void onAndroidDataLoaded(List<Article> articleList);

        void onAndroidDataLoadError();
}
