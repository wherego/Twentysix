package miuyongjun.twentysix.widget.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Video;

/**
 * Created by miaoyongjun on 2016/5/5.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class MVideoPlayer extends RelativeLayout {

    private final int MSG_HIDE_CONTROLLER = 10;
    private final int MSG_UPDATE_PLAY_TIME = 11;

    private Context mContext;
    private VideoView videoView;
    private MediaController mMediaController;
    private Timer mUpdateTimer;
    private VideoPlayCallbackImpl mVideoPlayCallback;

    private View mProgressBarView;
    private View mCloseBtnView;
    private Video mNowPlayVideo;



    private MediaController.PageType mCurrPageType = MediaController.PageType.SCALE;

    /**
     * automatically hide the control bar
     */
    private boolean mAutoHideController = true;

    private int currentTime;


    public MVideoPlayer(Context context) {
        super(context);
        initView(context);
    }

    public MVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View.inflate(context, R.layout.video_player_layout, this);
        videoView = (VideoView) findViewById(R.id.video_view);
        mMediaController = (MediaController) findViewById(R.id.controller);
        mProgressBarView = findViewById(R.id.progressbar);
        mCloseBtnView = findViewById(R.id.video_close_view);

        mMediaController.setMediaControl(mMediaControl);
        videoView.setOnTouchListener(mOnTouchVideoListener);
        showProgressView(false);

        mCloseBtnView.setOnClickListener(mOnClickListener);
    }

    private Handler mHandler = new Handler(msg -> {
        if (msg.what == MSG_UPDATE_PLAY_TIME) {
            updatePlayTime();
            updatePlayProgress();
        } else if (msg.what == MSG_HIDE_CONTROLLER) {
            showOrHideController();
        }
        return false;
    });


    private View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.video_close_view) {
                mVideoPlayCallback.onCloseVideo();
            }
        }
    };

    private View.OnTouchListener mOnTouchVideoListener = (view, motionEvent) -> {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            showOrHideController();
        }
        return mCurrPageType == MediaController.PageType.MAXIMIZE;
    };

    private MediaController.MediaControlImpl mMediaControl = new MediaController.MediaControlImpl() {
        @Override
        public void alwaysShowController() {
            MVideoPlayer.this.alwaysShowController();
        }

        @Override
        public void onSelectSrc(int position) {
            loadAndPlay(mNowPlayVideo, 0);
        }

        @Override
        public void onSelectFormat(int position) {
        }

        @Override
        public void onPlayTurn() {
            if (videoView.isPlaying()) {
                pausePlay(true);
            } else {
                goOnPlay();
            }
        }

        @Override
        public void onPageTurn() {
            mVideoPlayCallback.onSwitchPageType();
        }

        @Override
        public void onProgressTurn(MediaController.ProgressState state, int progress,boolean isFromUser) {
            if (state.equals(MediaController.ProgressState.START)) {
                mHandler.removeMessages(MSG_HIDE_CONTROLLER);
            } else if (state.equals(MediaController.ProgressState.STOP)) {
                resetHideTimer();
            } else {
                int time = progress * videoView.getDuration() / 100;
                if (!isFromUser) {
                    currentTime = time;
                }else{
                    videoView.seekTo(time);
                    updatePlayTime();
                }
            }
        }
    };

    public int getCurrentPosition() {
        return currentTime;
    }


    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START
                            || what == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
                        mProgressBarView.setVisibility(View.GONE);
                        setCloseButton(true);
                        return true;
                    }
                    return false;
                }
            });

        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            stopUpdateTimer();
            stopHideTimer(true);
            mMediaController.playFinish(videoView.getDuration());
            mVideoPlayCallback.onPlayFinish();
        }
    };

    public void setVideoPlayCallback(VideoPlayCallbackImpl videoPlayCallback) {
        mVideoPlayCallback = videoPlayCallback;
    }

    public View getCloseBtnView() {
        return mCloseBtnView;
    }

    @SuppressWarnings("unused")
    public VideoView getVideoView() {
        return videoView;
    }

    public void setPageType(MediaController.PageType pageType) {
        mMediaController.setPageType(pageType);
        mCurrPageType = pageType;
    }

    public void loadVideo(Video video, int seekTime) {
        if (mNowPlayVideo != null) {
            close();
        }
        mNowPlayVideo = video;
        loadAndPlay(mNowPlayVideo, seekTime);
    }


    public void pausePlay(boolean isShowController) {
        videoView.pause();
        mMediaController.setPlayState(MediaController.PlayState.PAUSE);
        stopHideTimer(isShowController);
    }


    public void goOnPlay() {
        videoView.start();
        mMediaController.setPlayState(MediaController.PlayState.PLAY);
        resetHideTimer();
        resetUpdateTimer();
    }


    public void close() {
        mMediaController.setPlayState(MediaController.PlayState.PAUSE);
        stopHideTimer(true);
        stopUpdateTimer();
        videoView.pause();
        videoView.stopPlayback();
        videoView.setVisibility(GONE);
    }


    public boolean isAutoHideController() {
        return mAutoHideController;
    }

    public void setAutoHideController(boolean autoHideController) {
        mAutoHideController = autoHideController;
    }


    private void setCloseButton(boolean isShow) {
        mCloseBtnView.setVisibility(isShow ? VISIBLE : INVISIBLE);
    }


    private void loadAndPlay(Video video, int seekTime) {
        showProgressView(seekTime > 0);
        setCloseButton(true);
        if (TextUtils.isEmpty(video.videoUrl)) {
            return;
        }
        videoView.setOnPreparedListener(mOnPreparedListener);
        videoView.setVideoPath(video.videoUrl);
        videoView.setVisibility(VISIBLE);
        startPlayVideo(seekTime);
    }


    private void startPlayVideo(int seekTime) {
        if (null == mUpdateTimer) resetUpdateTimer();
        resetHideTimer();
        videoView.setOnCompletionListener(mOnCompletionListener);
        videoView.start();
        if (seekTime > 0) {
            videoView.seekTo(seekTime);
        }
        mMediaController.setPlayState(MediaController.PlayState.PLAY);
    }


    private void updatePlayTime() {
        int allTime = videoView.getDuration();
        int playTime = videoView.getCurrentPosition();
        mMediaController.setPlayProgressTxt(playTime, allTime);
    }


    private void updatePlayProgress() {
        int allTime = videoView.getDuration();
        int playTime = videoView.getCurrentPosition();
        int loadProgress = videoView.getBufferPercentage();
        int progress = playTime * 100 / allTime;
        mMediaController.setProgressBar(progress, loadProgress);
    }


    private void showProgressView(Boolean isTransparentBg) {
        mProgressBarView.setVisibility(VISIBLE);
        if (!isTransparentBg) {
            mProgressBarView.setBackgroundResource(android.R.color.black);
        } else {
            mProgressBarView.setBackgroundResource(android.R.color.transparent);
        }
    }

    private void showOrHideController() {
        if (mMediaController.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.anim_exit_from_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mMediaController.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mMediaController.startAnimation(animation);
        } else {
            mMediaController.setVisibility(View.VISIBLE);
            mMediaController.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.anim_enter_from_bottom);
            mMediaController.startAnimation(animation);
            resetHideTimer();
        }
    }

    private void alwaysShowController() {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mMediaController.setVisibility(View.VISIBLE);
    }

    private void resetHideTimer() {
        if (!isAutoHideController()) return;
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, 3000);
    }

    private void stopHideTimer(boolean isShowController) {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mMediaController.clearAnimation();
        mMediaController.setVisibility(isShowController ? View.VISIBLE : View.GONE);
    }

    private void resetUpdateTimer() {
        mUpdateTimer = new Timer();
        int TIME_UPDATE_PLAY_TIME = 1000;
        mUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_UPDATE_PLAY_TIME);
            }
        }, 0, TIME_UPDATE_PLAY_TIME);
    }

    private void stopUpdateTimer() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
    }


    public interface VideoPlayCallbackImpl {
        void onCloseVideo();

        void onSwitchPageType();

        void onPlayFinish();
    }

}