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
import miuyongjun.twentysix.bean.gank.GankEntity;
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
public class GirlsFragment extends RecyclerBaseFragment implements GirlsContract.View {
    boolean isLinearManager;
    GirlsRecyclerViewAdapter girlsRecyclerViewAdapter;
    List<GankEntity> girlsEntityList = new ArrayList<>();
    private GirlsContract.Presenter mPresenter;

    public GirlsFragment() {

    }

    public GirlsFragment(boolean isLinearManager) {
        this.isLinearManager = isLinearManager;
    }


    @Override
    public void getData() {
        mPresenter.loadData(pageIndex);
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


    @Override
    public void showNoData() {
        isNoData = true;
        girlsRecyclerViewAdapter.removeFootView();
    }

    @Override
    public void showData(List<GankEntity> gankEntities) {
        girlsEntityList.addAll(gankEntities);
        girlsRecyclerViewAdapter.notifyDataSetChanged(girlsEntityList);
    }

    @Override
    public void showCompletedData() {
        if (mSwipeLayout != null) mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showLoadErrorData() {
        isNoData = true;
        girlsRecyclerViewAdapter.removeFootView();
    }

    @Override
    public void setPresenter(GirlsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
    }
}
