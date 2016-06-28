package miuyongjun.twentysix.ui.specialtopic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.adapter.SpecialTopicRecyclerViewAdapter;
import miuyongjun.twentysix.bean.bmob.SpecialTopic;
import miuyongjun.twentysix.ui.activity.TopicActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class SpecialTopicFragment extends RecyclerBaseFragment implements TopicContract.View {
    SpecialTopicRecyclerViewAdapter spRecyclerViewAdapter;
    List<SpecialTopic> spEntityList = new ArrayList<>();
    private TopicContract.Presenter mPresenter;

    @Override
    public void initAdapter() {
        spRecyclerViewAdapter = new SpecialTopicRecyclerViewAdapter(getActivity(), spEntityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(spRecyclerViewAdapter);
        spRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        Intent intent = new Intent(getActivity(), TopicActivity.class);
        intent.putExtra(TopicActivity.TOPIC_TITLE, spEntityList.get(position).title);
        intent.putExtra(TopicActivity.TOPIC_ID, spEntityList.get(position).getObjectId());
        startActivity(intent);
    }

    @Override
    public void getData() {
        mPresenter.loadData(pageIndex);
    }

    @Override
    public void cleanList() {
        if (spEntityList != null) spEntityList.clear();
    }

    @Override
    public String getImageUrl(int position) {
        return spEntityList.get(position).imageUrl;
    }

    @Override
    public String getImageName(int position) {
        return spEntityList.get(position).title;
    }


    @Override
    public void showData(List<SpecialTopic> spHotEntities) {
        mSwipeLayout.setRefreshing(false);
        spEntityList.addAll(spHotEntities);
        spRecyclerViewAdapter.notifyDataSetChanged(spEntityList);
    }

    @Override
    public void showNoData() {
        isNoData = true;
        spRecyclerViewAdapter.removeFootView();
    }

    @Override
    public void showCompletedData() {
        if (mSwipeLayout != null) mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showLoadErrorData() {
        isNoData = true;
        spRecyclerViewAdapter.removeFootView();
    }

    @Override
    public void setPresenter(TopicContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
