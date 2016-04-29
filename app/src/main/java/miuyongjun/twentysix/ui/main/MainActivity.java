package miuyongjun.twentysix.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.common.CommonFragmentPagerAdapter;
import miuyongjun.twentysix.ui.base.ToolbarActivity;
import miuyongjun.twentysix.ui.news.NewsFragment;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    CommonFragmentPagerAdapter mAdapter;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.tab_viewPager)
    ViewPager tabViewPager;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    private List<Fragment> mFragmentList;
    String[] tabTitles = new String[]{"新闻", "微信精选", "美女图片"};

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(R.string.app_name_num);
        initData();
        initViewPager();
        initTabLayout();
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            NewsFragment newsFragment = new NewsFragment();
            mFragmentList.add(newsFragment);
        }
    }

    private void initViewPager() {
        mAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), tabTitles, mFragmentList);
        tabViewPager.setAdapter(mAdapter);
        tabViewPager.setOffscreenPageLimit(13);
        tabViewPager.addOnPageChangeListener(this);
    }


    private void initTabLayout() {
        tabLayout.setupWithViewPager(tabViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
