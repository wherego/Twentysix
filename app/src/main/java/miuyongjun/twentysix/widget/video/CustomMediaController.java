package miuyongjun.twentysix.widget.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import miuyongjun.twentysix.R;

/**
 * Created by miaoyongjun on 2016/5/5.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class CustomMediaController extends FrameLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    /**
     * play button
     */
    private ImageView mPlayImg;
    /**
     * play progressbar
     */
    private SeekBar mProgressSeekBar;
    /**
     * play time
     */
    private TextView mTimeTxt;
    /**
     * maximize button
     */
    private ImageView mMaximizeImg;
    /**
     * scale button
     */
    public ImageView mScaleImg;

    private MediaControlImpl mMediaControl;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
        mMediaControl.onProgressTurn(ProgressState.DOING, progress, isFromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mMediaControl.onProgressTurn(ProgressState.START, 0,false);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMediaControl.onProgressTurn(ProgressState.STOP, 0,false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pause) {
            mMediaControl.onPlayTurn();
        } else if (view.getId() == R.id.maximize) {
            mMediaControl.onPageTurn();
        } else if (view.getId() == R.id.scale) {
            mMediaControl.onPageTurn();
        }
    }

    public void setProgressBar(int progress, int secondProgress) {
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;
        if (secondProgress < 0) secondProgress = 0;
        if (secondProgress > 100) secondProgress = 100;
        mProgressSeekBar.setProgress(progress);
        mProgressSeekBar.setSecondaryProgress(secondProgress);
    }

    public void setPlayState(PlayState playState) {
        mPlayImg.setImageResource(playState.equals(PlayState.PLAY) ? R.mipmap.ic_action_av_pause : R.mipmap.ic_action_av_play_arrow);
    }

    public void setPageType(PageType pageType) {
        mMaximizeImg.setVisibility(pageType.equals(PageType.MAXIMIZE) ? GONE : VISIBLE);
        mScaleImg.setVisibility(pageType.equals(PageType.SCALE) ? GONE : VISIBLE);
    }

    public void setPlayProgressTxt(int nowSecond, int allSecond) {
        mTimeTxt.setText(getPlayTime(nowSecond, allSecond));
    }

    public void playFinish(int allTime) {
        mProgressSeekBar.setProgress(0);
        setPlayProgressTxt(0, allTime);
        setPlayState(PlayState.PAUSE);
    }

    public void setMediaControl(MediaControlImpl mediaControl) {
        mMediaControl = mediaControl;
    }

    public CustomMediaController(Context context) {
        super(context);
        initView(context);
    }

    public CustomMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.video_media_controller, this);
        mPlayImg = (ImageView) findViewById(R.id.pause);
        mProgressSeekBar = (SeekBar) findViewById(R.id.media_controller_progress);
        mTimeTxt = (TextView) findViewById(R.id.time);
        mMaximizeImg = (ImageView) findViewById(R.id.maximize);
        mScaleImg = (ImageView) findViewById(R.id.scale);
        initData();
    }

    private void initData() {
        mProgressSeekBar.setOnSeekBarChangeListener(this);
        mPlayImg.setOnClickListener(this);
        mScaleImg.setOnClickListener(this);
        mMaximizeImg.setOnClickListener(this);
        setPageType(PageType.SCALE);
        setPlayState(PlayState.PAUSE);
    }

    @SuppressLint("SimpleDateFormat")
    private String formatPlayTime(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    private String getPlayTime(int playSecond, int allSecond) {
        String playSecondStr = "00:00";
        String allSecondStr = "00:00";
        if (playSecond > 0) {
            playSecondStr = formatPlayTime(playSecond);
        }
        if (allSecond > 0) {
            allSecondStr = formatPlayTime(allSecond);
        }
        return playSecondStr + "/" + allSecondStr;
    }

    /**
     * 播放样式 展开、缩放
     */
    public enum PageType {
        MAXIMIZE, SCALE
    }

    /**
     * 播放状态 播放 暂停
     */
    public enum PlayState {
        PLAY, PAUSE
    }

    public enum ProgressState {
        START, DOING, STOP
    }


    public interface MediaControlImpl {
        void onPlayTurn();

        void onPageTurn();

        void onProgressTurn(ProgressState state, int progress,boolean isFromUser);
    }

}
