package miuyongjun.twentysix.ui.gankdetail;

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
public class GankDetailFragment extends RecyclerBaseFragment {
    public static final String GANK_LIST = "gank_list";
    GankDetailAdapter gankDetailAdapter;
    List<GankEntity> gankEntityList = new ArrayList<>();


    private void parseArguments() {
        Bundle bundle = getArguments();
        gankEntityList = (List<GankEntity>) bundle.getSerializable(GANK_LIST);
    }

    @Override
    public void onViewCreatedBase() {
        parseArguments();
        mSwipeLayout.setEnabled(false);
    }

    @Override
    public void initAdapter() {
        gankDetailAdapter = new GankDetailAdapter(getActivity(), gankEntityList, false);
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

    }

    @Override
    public void cleanList() {
    }


}
