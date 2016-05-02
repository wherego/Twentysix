package miuyongjun.twentysix.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.GankDetailAdapter;
import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankListFragment extends RecyclerBaseFragment {
    public static final String EXTRA_GANK_TYPE = "gank_type";
    String type;
    GankDetailAdapter gankDetailAdapter;
    List<GankEntity> gankEntityList = new ArrayList<>();


    private void parseArguments() {
        Bundle bundle = getArguments();
        type = bundle.getString(EXTRA_GANK_TYPE);
    }

    @Override
    public void onViewCreatedBase() {
        parseArguments();
    }

    @Override
    public void initAdapter() {
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
        RetrofitUtil.getGankApi(type, pageIndex)
                .filter(this::handleFilter)
                .map(gankBaseEntity -> gankBaseEntity.gankEntityList)
                .subscribe(this::handleListData,
                        this::onError, () -> mSwipeLayout.setRefreshing(false));
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

    private boolean handleFilter(GankBaseEntity gankBaseEntity) {
        if (gankBaseEntity.gankEntityList == null) {
            handleListData(null);
            return false;
        }
        return true;
    }

    private void handleListData(List<GankEntity> gankEntities) {
        if (gankEntities == null || gankEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            gankDetailAdapter.removeFootView();
            return;
        }
        gankDetailAdapter.getFootView().setVisibility(
                gankEntities.size() < Constant.PAGE_SIZE
                        ? View.GONE : View.VISIBLE);

        gankEntityList.addAll(gankEntities);
        gankDetailAdapter.notifyDataSetChanged(gankEntityList);
    }
}
