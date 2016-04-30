package miuyongjun.twentysix.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import miuyongjun.twentysix.R;

/**
 * Created by miuyongjun on 16/4/29.
 */
public abstract class ToolbarActivity extends BaseActivity {

    abstract protected int provideContentViewId();


    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("No toolbar");
        }

        setSupportActionBar(mToolbar);

        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    public void setAppBarElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(elevation);
        }
    }

    public boolean canBack() {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }
}