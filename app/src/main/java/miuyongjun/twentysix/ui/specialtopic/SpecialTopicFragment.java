package miuyongjun.twentysix.ui.specialtopic;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import miuyongjun.twentysix.adapter.SpecialTopicRecyclerViewAdapter;
import miuyongjun.twentysix.bean.bmob.SpecialTopic;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.ui.activity.TopicActivity;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class SpecialTopicFragment extends RecyclerBaseFragment {
    SpecialTopicRecyclerViewAdapter spRecyclerViewAdapter;
    List<SpecialTopic> spEntityList = new ArrayList<>();


    @Override
    public void initAdapter() {
        spRecyclerViewAdapter = new SpecialTopicRecyclerViewAdapter(getActivity(), spEntityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(spRecyclerViewAdapter);
        spRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v,int position) {
        Intent intent = new Intent(getActivity(), TopicActivity.class);
        intent.putExtra(TopicActivity.TOPIC_TITLE,spEntityList.get(position).title);
        intent.putExtra(TopicActivity.TOPIC_ID,spEntityList.get(position).getObjectId());
        startActivity(intent);
    }

    @Override
    public void getData() {
        BmobQuery<SpecialTopic> bmobQuery = new BmobQuery<>();
        bmobQuery.setSkip(Constant.PAGE_SIZE * (pageIndex - 1));
        bmobQuery.setLimit(Constant.PAGE_SIZE * pageIndex);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(getActivity(), new FindListener<SpecialTopic>() {
            @Override
            public void onSuccess(List<SpecialTopic> list) {
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


    private void handleListData(List<SpecialTopic> spHotEntities) {
        if (spHotEntities == null) {
            isNoData = true;
            spRecyclerViewAdapter.removeFootView();
            return;
        } else if (spHotEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            spRecyclerViewAdapter.removeFootView();
        }
        spEntityList.addAll(spHotEntities);
        spRecyclerViewAdapter.notifyDataSetChanged(spEntityList);
    }
}
