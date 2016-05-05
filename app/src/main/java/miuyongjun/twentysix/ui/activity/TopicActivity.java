package miuyongjun.twentysix.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.ui.android.AndroidFragment;
import miuyongjun.twentysix.ui.android.AndroidPresenter;
import miuyongjun.twentysix.ui.base.ToolbarActivity;

/**
 * Created by miaoyongjun on 16/5/5.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class TopicActivity extends ToolbarActivity {

    public final static String TOPIC_TITLE = "topic_title";
    public final static String TOPIC_ID = "topic_id";
    private AndroidPresenter androidPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fragment;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle(getIntent().getStringExtra(TOPIC_TITLE));
        String topicId = getIntent().getStringExtra(TOPIC_ID);
        AndroidFragment androidFragment = new AndroidFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AndroidFragment.TOPIC_ID, topicId);
        androidFragment.setArguments(bundle);
        androidPresenter = new AndroidPresenter(androidFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentPanel, androidFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
