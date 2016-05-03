package miuyongjun.twentysix.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.common.bus.ChooseDate;
import miuyongjun.twentysix.ui.base.ToolbarActivity;
import miuyongjun.twentysix.ui.gank.GankFragment;
import miuyongjun.twentysix.ui.healthadvisory.HealthAdvisoryFragment;
import miuyongjun.twentysix.ui.home.HomeFragment;
import miuyongjun.twentysix.utils.BusUtil;
import miuyongjun.twentysix.utils.DoubleClickExitHelper;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DoubleClickExitHelper mDoubleClickExitHelper;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

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
        MenuItem item = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);
        setAppBarElevation(0f);
        setTitle(R.string.app_name);
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusUtil.getBusInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusUtil.getBusInstance().unregister(this);
    }

    @Override
    public void onBackPressed() {
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
        if (id == R.id.action_date) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int monthOfYear = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            android.app.DatePickerDialog pickerDialog = new android.app.DatePickerDialog(
                    MainActivity.this, (view, year1, monthOfYear1, dayOfMonth1) ->
                    BusUtil.getBusInstance().post(new ChooseDate(year1, monthOfYear1, dayOfMonth1)), year, monthOfYear, dayOfMonth);
            pickerDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        setAppBarElevation(10f);
        switch (item.getItemId()) {
            case R.id.nav_home:
                mToolbar.getMenu().clear();
                mToolbar.inflateMenu(R.menu.main);
                setAppBarElevation(0f);
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_gank:
                mToolbar.getMenu().clear();
                mToolbar.inflateMenu(R.menu.gank);
                fragmentClass = GankFragment.class;
                break;
            default:
                setAppBarElevation(0f);
                fragmentClass = HealthAdvisoryFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExitHelper.onKeyDown(keyCode, navigationView);
        }
        return flag;
    }
}
