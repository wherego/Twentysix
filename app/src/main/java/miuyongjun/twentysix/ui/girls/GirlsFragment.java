package miuyongjun.twentysix.ui.girls;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.GirlsRecyclerViewAdapter;
import miuyongjun.twentysix.bean.gank.GankBaseEntity;
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;
import miuyongjun.twentysix.ui.gankdetail.GankDetailActivity;
import miuyongjun.twentysix.utils.UIUtils;
import miuyongjun.twentysix.widget.RecyclerSpanSizeLookup;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
@SuppressLint("ValidFragment")
public class GirlsFragment extends RecyclerBaseFragment {
    boolean isLinearManager;
    GirlsRecyclerViewAdapter girlsRecyclerViewAdapter;
    List<GankEntity> girlsEntityList = new ArrayList<>();

    public GirlsFragment(boolean isLinearManager) {
        this.isLinearManager = isLinearManager;
    }

    @Override
    public void onViewCreatedBase() {
    }

    @Override
    public void getData() {
        RetrofitUtil.getGirlsApi(Constant.GANK_FULI, pageIndex)
                .filter(this::handleFilter)
                .map(gankBaseEntity -> gankBaseEntity.gankEntityList)
                .subscribe(this::handleListData, this::onError, () -> mSwipeLayout.setRefreshing(false));
    }

    @Override
    public void cleanList() {
        if (girlsEntityList != null) girlsEntityList.clear();
    }

    @Override
    public void initAdapter() {
        girlsRecyclerViewAdapter = new GirlsRecyclerViewAdapter(getActivity(), girlsEntityList);
        if (isLinearManager) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            manager.setSpanSizeLookup(new RecyclerSpanSizeLookup(girlsRecyclerViewAdapter));
            mRecyclerView.setLayoutManager(manager);
        }
        mRecyclerView.setAdapter(girlsRecyclerViewAdapter);
        girlsRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        Intent intent =
                new Intent(getActivity(), GankDetailActivity.class);
        intent.putExtra(GankDetailActivity.EXTRA_GANK_DATE, girlsEntityList.get(position).publishedAt);
        intent.putExtra(GankDetailActivity.EXTRA_GANK_IMAGE_URL, girlsEntityList.get(position).url);
        LinearLayout linearLayout = (LinearLayout) ((CardView) v).getChildAt(0);
        UIUtils.intentWithTransition(getActivity(), intent, linearLayout.getChildAt(0));
    }


    @Override
    public String getImageUrl(int position) {
        return girlsEntityList.get(position).url;
    }

    @Override
    public String getImageName(int position) {
        return girlsEntityList.get(position).desc;
    }

    private boolean handleFilter(GankBaseEntity gankBaseEntity) {
        if (gankBaseEntity.gankEntityList == null) {
            handleListData(null);
            return false;
        }
        return true;
    }

    private void handleListData(List<GankEntity> girlsEntities) {
        if (girlsEntities == null || girlsEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            girlsRecyclerViewAdapter.removeFootView();
            return;
        }
        girlsRecyclerViewAdapter.getFootView().setVisibility(
                girlsEntities.size() < Constant.PAGE_SIZE
                        ? View.GONE : View.VISIBLE);

        girlsEntityList.addAll(girlsEntities);
        girlsRecyclerViewAdapter.notifyDataSetChanged(girlsEntityList);
    }
}
