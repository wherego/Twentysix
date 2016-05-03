package miuyongjun.twentysix.ui.android;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import miuyongjun.twentysix.adapter.AndroidRecyclerViewAdapter;
import miuyongjun.twentysix.bean.bmob.Article;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidFragment extends RecyclerBaseFragment {
    AndroidRecyclerViewAdapter androidRecyclerViewAdapter;

    List<Article> newsEntityList = new ArrayList<>();

    @Override
    public void onViewCreatedBase() {

    }

    @Override
    public void initAdapter() {
        androidRecyclerViewAdapter = new AndroidRecyclerViewAdapter(getActivity(), newsEntityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(androidRecyclerViewAdapter);
        androidRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        Intent intent = WebActivity.newIntent(getActivity(), newsEntityList.get(position).link,
                newsEntityList.get(position).title);
        startActivity(intent);
    }

    @Override
    public void getData() {
        BmobQuery<Article> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(5);
        bmobQuery.findObjects(getActivity(), new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                mSwipeLayout.setRefreshing(false);
                handleListData(list);
            }

            @Override
            public void onError(int i, String s) {
                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public String getImageUrl(int position) {
        return newsEntityList.get(position).imageUrl;
    }

    @Override
    public String getImageName(int position) {
        return newsEntityList.get(position).title;
    }

    @Override
    public void cleanList() {
        if (newsEntityList != null) newsEntityList.clear();
    }


    private void handleListData(List<Article> articleEntities) {
        if (articleEntities == null) {
            isNoData = true;
            androidRecyclerViewAdapter.removeFootView();
            return;
        } else if (articleEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            androidRecyclerViewAdapter.removeFootView();
        }
        androidRecyclerViewAdapter.getFootView().setVisibility(
                articleEntities.size() < Constant.PAGE_SIZE
                        ? View.GONE : View.VISIBLE);
        newsEntityList.addAll(articleEntities);
        androidRecyclerViewAdapter.notifyDataSetChanged(newsEntityList);
    }

}
