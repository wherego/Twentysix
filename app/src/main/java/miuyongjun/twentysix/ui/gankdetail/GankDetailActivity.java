package miuyongjun.twentysix.ui.gankdetail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.gank.GankDateEntity;
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.CommonFragmentPagerAdapter;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import miuyongjun.twentysix.utils.DateUtils;
import miuyongjun.twentysix.utils.ToastUtils;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GANK_DATE = "gank_date";
    public static final String EXTRA_GANK_IMAGE_URL = "gank_url";
    Date mGankDate;
    String imageUrl;
    @Bind(R.id.iv_shared_transition)
    RatioImageView iv_shared_transition;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.tab_viewPager)
    ViewPager tabViewPager;
    @Bind(R.id.progress)
    CircleProgressBar progressBar;
    private List<Fragment> mFragmentList;
    String[] tabTitles;
    CommonFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGankDate = (Date) getIntent().getSerializableExtra(EXTRA_GANK_DATE);
        imageUrl = getIntent().getStringExtra(EXTRA_GANK_IMAGE_URL);
        collapsing.setTitle(DateUtils.toDate(mGankDate));
        initData();
    }

    private void initData() {
        Picasso.with(this).load(imageUrl).into(iv_shared_transition);
        tabTitles = getResources().getStringArray(R.array.gank_detail_list);
        mFragmentList = new ArrayList<>();
        getData();
    }

    private void getData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mGankDate);
        RetrofitUtil.getGankBaseApi(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH))
                .filter(gankDateBaseEntity -> gankDateBaseEntity.results != null)
                .map(gankDateBaseEntity -> gankDateBaseEntity.results)
                .subscribe(this::addFragment,
                        throwable -> ToastUtils.showSnakbar(R.string.retry, tabLayout),
                        this::initViewPager);
    }

    private void addFragment(GankDateEntity gankDateEntity) {
        progressBar.setVisibility(View.GONE);
        List<List<GankEntity>> listList = new ArrayList<>();
        listList.add(gankDateEntity.androidList);
        listList.add(gankDateEntity.iosList);
        listList.add(gankDateEntity.tuozhangList);
        for (int i = 0; i < listList.size(); i++) {
            GankDetailFragment gankDetailFragment = new GankDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(GankDetailFragment.GANK_LIST, (Serializable) listList.get(i));
            gankDetailFragment.setArguments(bundle);
            mFragmentList.add(gankDetailFragment);
        }
    }

    private void initViewPager() {
        mAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), tabTitles, mFragmentList);
        tabViewPager.setAdapter(mAdapter);
        tabViewPager.setOffscreenPageLimit(tabTitles.length);
        tabLayout.setupWithViewPager(tabViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
