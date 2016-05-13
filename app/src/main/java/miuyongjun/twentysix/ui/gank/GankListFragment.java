package miuyongjun.twentysix.ui.gank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.GankDetailAdapter;
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankListFragment extends RecyclerBaseFragment implements GankContract.View {
    public static final String EXTRA_GANK_TYPE = "gank_type";
    String type;
    GankDetailAdapter gankDetailAdapter;
    List<GankEntity> gankEntityList = new ArrayList<>();
    private GankContract.Presenter mPresenter;


    private void parseArguments() {
        Bundle bundle = getArguments();
        type = bundle.getString(EXTRA_GANK_TYPE);
    }


    @Override
    public void initAdapter() {
        parseArguments();
        gankDetailAdapter = new GankDetailAdapter(getActivity(), gankEntityList, true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(gankDetailAdapter);
        gankDetailAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        Intent intent = WebActivity.newIntent(getActivity(), gankEntityList.get(position).url,
                gankEntityList.get(position).desc);
        startActivity(intent);
    }

    @Override
    public void getData() {
        mPresenter.loadData(type, pageIndex);
    }

    @Override
    public void cleanList() {
        if (gankEntityList != null) gankEntityList.clear();
    }

    @Override
    public String getImageUrl(int position) {
        return gankEntityList.get(position).url;
    }

    @Override
    public String getImageName(int position) {
        return gankEntityList.get(position).desc;
    }


    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showNoData() {
        isNoData = true;
        gankDetailAdapter.removeFootView();
    }

    @Override
    public void showData(List<GankEntity> gankEntities) {
        gankEntityList.addAll(gankEntities);
        gankDetailAdapter.notifyDataSetChanged(gankEntityList);
    }

    @Override
    public void showCompletedData() {
        if (mSwipeLayout != null) mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showLoadErrorData() {
        isNoData = true;
        gankDetailAdapter.removeFootView();
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
    }
}
