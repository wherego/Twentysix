package miuyongjun.twentysix.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    String[] mList;

    public CommonFragmentPagerAdapter(FragmentManager fragmentManager, String[] mList, List<Fragment> mFragmentList) {
        super(fragmentManager);
        this.mList = mList;
        this.mFragmentList = mFragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mList.length;
    }
}
