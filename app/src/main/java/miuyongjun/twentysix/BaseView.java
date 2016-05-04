package miuyongjun.twentysix;

import android.content.Context;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public interface BaseView<T> {

    Context getContext();
    void setPresenter(T presenter);

}
