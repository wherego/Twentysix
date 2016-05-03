package miuyongjun.twentysix.ui.wxhot;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.WXHotRecyclerViewAdapter;
import miuyongjun.twentysix.bean.wechat.WXHotBaseEntity;
import miuyongjun.twentysix.bean.wechat.WXHotEntity;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;
import miuyongjun.twentysix.widget.RecyclerSpanSizeLookup;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class WXHotFragment extends RecyclerBaseFragment {
    WXHotRecyclerViewAdapter wxhotRecyclerViewAdapter;
    List<WXHotEntity> wxHotEntityList = new ArrayList<>();


    @Override
    public void initAdapter() {
        wxhotRecyclerViewAdapter = new WXHotRecyclerViewAdapter(getActivity(), wxHotEntityList);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new RecyclerSpanSizeLookup(wxhotRecyclerViewAdapter));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(wxhotRecyclerViewAdapter);
        wxhotRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v,int position) {
        Intent intent = WebActivity.newIntent(getActivity(), wxHotEntityList.get(position).url,
                wxHotEntityList.get(position).title);
        startActivity(intent);
    }

    @Override
    public void getData() {
        RetrofitUtil.getWXHotApi(pageIndex)
                .filter(this::handleFilter)
                .map(wxHotBaseEntity ->wxHotBaseEntity.wxHotEntityList)
                .subscribe(this::handleListData,
                        this::onError, () -> mSwipeLayout.setRefreshing(false));
    }

    @Override
    public void cleanList() {
        if (wxHotEntityList != null) wxHotEntityList.clear();
    }

    @Override
    public String getImageUrl(int position) {
        return wxHotEntityList.get(position).picUrl;
    }

    @Override
    public String getImageName(int position) {
        return wxHotEntityList.get(position).title;
    }

    private boolean handleFilter(WXHotBaseEntity wxHotBaseEntity) {
        if (wxHotBaseEntity.wxHotEntityList == null) {
            handleListData(null);
            return false;
        }
        return true;
    }

    private void handleListData(List<WXHotEntity> wxHotEntities) {
        if (wxHotEntities == null || wxHotEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            wxhotRecyclerViewAdapter.removeFootView();
            return;
        }
        wxHotEntityList.addAll(wxHotEntities);
        wxhotRecyclerViewAdapter.notifyDataSetChanged(wxHotEntityList);
    }
}
