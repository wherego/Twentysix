package miuyongjun.twentysix.ui.news;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.NewsRecyclerViewAdapter;
import miuyongjun.twentysix.bean.news.NewsBaseEntity;
import miuyongjun.twentysix.bean.news.NewsEntity;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;
import miuyongjun.twentysix.widget.RecyclerSpanSizeLookup;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class NewsFragment extends RecyclerBaseFragment {
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    List<NewsEntity> newsEntityList = new ArrayList<>();

    @Override
    public void onViewCreatedBase() {

    }

    @Override
    public void initAdapter() {
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity(), newsEntityList);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new RecyclerSpanSizeLookup(newsRecyclerViewAdapter));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(newsRecyclerViewAdapter);
        newsRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        Intent intent = WebActivity.newIntent(getActivity(), newsEntityList.get(position).link,
                newsEntityList.get(position).title);
        startActivity(intent);
    }

    @Override
    public void getData() {
        String json = "{'device':'android','catid':" + pageIndex + ",'pagesize':" + Constant.PAGE_SIZE + ",'sid':'11142'}";
        RetrofitUtil.getNewsApi(json)
                .filter(this::handleFilter)
                .map(newsBaseEntity -> newsBaseEntity.newsEntityList)
                .subscribe(this::handleListData,
                        this::onError, () -> mSwipeLayout.setRefreshing(false));
    }

    @Override
    public String getImageUrl(int position) {
        return newsEntityList.get(position).thumb;
    }

    @Override
    public String getImageName(int position) {
        return newsEntityList.get(position).title;
    }

    @Override
    public void cleanList() {
        if (newsEntityList != null) newsEntityList.clear();
    }

    private boolean handleFilter(NewsBaseEntity newsBaseEntity) {
        if (newsBaseEntity.newsEntityList == null) {
            handleListData(null);
            return false;
        }
        return true;
    }

    private void handleListData(List<NewsEntity> newsEntities) {
        if (newsEntities == null || newsEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            newsRecyclerViewAdapter.removeFootView();
            return;
        }
        newsRecyclerViewAdapter.getFootView().setVisibility(
                newsEntities.size() < Constant.PAGE_SIZE
                        ? View.GONE : View.VISIBLE);
        newsEntityList.addAll(newsEntities);
        newsRecyclerViewAdapter.notifyDataSetChanged(newsEntityList);
    }

}
