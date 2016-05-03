package miuyongjun.twentysix.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.common.CommonFragmentPagerAdapter;
import miuyongjun.twentysix.ui.girls.GirlsFragment;
import miuyongjun.twentysix.ui.android.AndroidFragment;
import miuyongjun.twentysix.ui.wxhot.WXHotFragment;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class HomeFragment extends Fragment {
    CommonFragmentPagerAdapter mAdapter;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.tab_viewPager)
    ViewPager tabViewPager;
    private List<Fragment> mFragmentList;
    String[] tabTitles;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initViewPager();
        initTabLayout();
    }

    private void initData() {
        tabTitles = getActivity().getResources().getStringArray(R.array.home_list);
        mFragmentList = new ArrayList<>();
        AndroidFragment androidFragment = new AndroidFragment();
        mFragmentList.add(androidFragment);
        WXHotFragment wxHotFragment = new WXHotFragment();
        mFragmentList.add(wxHotFragment);
        GirlsFragment girlsFragment = new GirlsFragment(false);
        mFragmentList.add(girlsFragment);

    }

    private void initViewPager() {
        mAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), tabTitles, mFragmentList);
        tabViewPager.setAdapter(mAdapter);
        tabViewPager.setOffscreenPageLimit(tabTitles.length);
    }


    private void initTabLayout() {
        tabLayout.setupWithViewPager(tabViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
