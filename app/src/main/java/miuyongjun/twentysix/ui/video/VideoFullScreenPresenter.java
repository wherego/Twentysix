package miuyongjun.twentysix.ui.video;

/**
 * Created by miaoyongjun on 16/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class VideoFullScreenPresenter implements VideoFullScreenContract.Presenter {

    private final VideoFullScreenContract.View mAndroidView;

    public VideoFullScreenPresenter(VideoFullScreenContract.View mAndroidView) {
        this.mAndroidView = mAndroidView;
        mAndroidView.setPresenter(this);
    }


    @Override
    public void start() {

    }


    @Override
    public void loadData() {

    }
}
