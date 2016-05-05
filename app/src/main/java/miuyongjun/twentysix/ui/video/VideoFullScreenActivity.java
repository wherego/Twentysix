package miuyongjun.twentysix.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Video;
import miuyongjun.twentysix.widget.video.MVideoPlayer;

/**
 * Created by miaoyongjun on 2016/5/5.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class VideoFullScreenActivity extends AppCompatActivity implements VideoFullScreenContract.View {

    public final static String VIDEO_OBJECT = "video_object";
    public final static String SEEK_TIME = "seek_time";

    private Video video;
    private int seekTime;

    @Bind(R.id.video_player)
    MVideoPlayer mVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);
        ButterKnife.bind(this);
        parseIntent();
        LoadData();
    }

    public static Intent newIntent(Context context, Video video, int seekTime) {
        Intent intent = new Intent(context, VideoFullScreenActivity.class);
        intent.putExtra(VIDEO_OBJECT, video);
        intent.putExtra(SEEK_TIME, seekTime);
        return intent;
    }

    private void parseIntent() {
        video = (Video) getIntent().getSerializableExtra(VIDEO_OBJECT);
        seekTime = getIntent().getIntExtra(SEEK_TIME, 0);
    }


    private void LoadData() {
        mVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        mVideoPlayer.setVisibility(View.VISIBLE);
        mVideoPlayer.setAutoHideController(false);
        mVideoPlayer.loadVideo(video, seekTime);
    }

    private MVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new MVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mVideoPlayer.close();
        }

        @Override
        public void onSwitchPageType() {
        }

        @Override
        public void onPlayFinish() {
            mVideoPlayer.close();
        }
    };


    @Override
    public void showLoadErrorData() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setPresenter(VideoFullScreenContract.Presenter presenter) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
