package miuyongjun.twentysix.ui.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.pavlospt.CircleView;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.R;
import miuyongjun.twentysix.adapter.AndroidRecyclerViewAdapter;
import miuyongjun.twentysix.bean.bmob.Article;
import miuyongjun.twentysix.ui.activity.WebActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;
import miuyongjun.twentysix.utils.ToastUtils;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidFragment extends RecyclerBaseFragment implements AndroidContract.View {

    private AndroidContract.Presenter mPresenter;

    AndroidRecyclerViewAdapter androidRecyclerViewAdapter;

    List<Article> newsEntityList = new ArrayList<>();


    @Override
    public void initAdapter() {
        androidRecyclerViewAdapter = new AndroidRecyclerViewAdapter(getActivity(), newsEntityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(androidRecyclerViewAdapter);
        androidRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        String url;
        if (v instanceof CircleView || v instanceof TextView) {
            url = newsEntityList.get(position).authorLink;
        } else {
            url = newsEntityList.get(position).link;
        }
        if (url == null) {
            ToastUtils.showSnakbar(R.string.no_author_link, mRecyclerView);
            return;
        }
        Intent intent = WebActivity.newIntent(getActivity(), url,
                newsEntityList.get(position).title);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void getData() {
        mPresenter.loadData(pageIndex);
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


    @Override
    public void showNoData() {
        isNoData = true;
        androidRecyclerViewAdapter.removeFootView();
        ToastUtils.showSnakbar(R.string.no_data, mRecyclerView);
    }

    @Override
    public void showData(List<Article> articleEntities) {
        newsEntityList.addAll(articleEntities);
        androidRecyclerViewAdapter.notifyDataSetChanged(newsEntityList);
    }

    @Override
    public void showCompletedData() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showLoadErrorData() {
        isNoData = true;
        androidRecyclerViewAdapter.removeFootView();
    }

    @Override
    public void setPresenter(AndroidContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
