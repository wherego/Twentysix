package miuyongjun.twentysix.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.InputStream;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Video;
import miuyongjun.twentysix.widget.video.MVideoPlayer;
import miuyongjun.twentysix.widget.video.MediaController;

/**
 * Created by miaoyongjun on 2016/5/5.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class VideoFullScreenActivity extends AppCompatActivity implements VideoFullScreenContract.View {

    public final static String VIDEO_OBJECT = "video_object";
    public final static String SEEK_TIME = "seek_time";
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;
    @Bind(R.id.sv_danmaku)
    DanmakuView mDanmakuView;
    @Bind(R.id.btn_hide)
    Button btnHide;
    @Bind(R.id.btn_show)
    Button btnShow;

    private Video video;
    private int seekTime;

    @Bind(R.id.video_player)
    MVideoPlayer mVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        mVideoPlayer.getCloseBtnView().setVisibility(View.GONE);
        mVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        mVideoPlayer.setVisibility(View.VISIBLE);
        mVideoPlayer.setPageType(MediaController.PageType.SCALE);
        mVideoPlayer.setAutoHideController(true);
        mVideoPlayer.loadVideo(video, seekTime);
        initDanmaKu();
    }

    private void initDanmaKu() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
                @Override
                public void onDanmakuClick(BaseDanmaku latest) {
                    Log.d("DFM", "onDanmakuClick text:" + latest.text);
                }

                @Override
                public void onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick danmakus size:" + danmakus.size());
                }
            });
            mDanmakuView.prepare(mParser, mContext);
            mDanmakuView.showFPS(true);
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    private MVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new MVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {

        }

        @Override
        public void onSwitchPageType() {
            mVideoPlayer.close();
            finish();
        }

        @Override
        public void onPlayFinish() {
            mVideoPlayer.close();
            finish();
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

    @OnClick(R.id.btn_hide)
    public void HideDanmaku() {
        mDanmakuView.hide();
    }

    @OnClick(R.id.btn_show)
    public void ShowDanmaku() {
        mDanmakuView.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
