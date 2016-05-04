package miuyongjun.twentysix.ui.video;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.adapter.VideoRecyclerViewAdapter;
import miuyongjun.twentysix.bean.bmob.Video;
import miuyongjun.twentysix.common.Constant;
import miuyongjun.twentysix.ui.base.RecyclerBaseFragment;
import miuyongjun.twentysix.utils.ToastUtils;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class VideoFragment extends RecyclerBaseFragment {
    VideoRecyclerViewAdapter spRecyclerViewAdapter;
    List<Video> videoEntityList = new ArrayList<>();


    @Override
    public void initAdapter() {
        spRecyclerViewAdapter = new VideoRecyclerViewAdapter(getActivity(), videoEntityList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(spRecyclerViewAdapter);
        spRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    public void cardViewClick(View v, int position) {
        if (v instanceof ImageView) {
            v.setVisibility(View.GONE);
            VideoView videoView = (VideoView) ((FrameLayout) v.getParent()).getChildAt(1);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath("http://bmob-cdn-982.b0.upaiyun.com/2016/05/04/8b5a2bc8409ce87180f3c687e73b7811.mp4");
            MediaController mediaController = new MediaController(getActivity());
            videoView.setMediaController(mediaController);
            mediaController.setMediaPlayer(videoView);
            videoView.requestFocus();
        }
    }

    @Override
    public void getData() {
        BmobQuery<Video> bmobQuery = new BmobQuery<>();
        bmobQuery.setSkip(Constant.PAGE_SIZE * (pageIndex - 1));
        bmobQuery.setLimit(Constant.PAGE_SIZE * pageIndex);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(getActivity(), new FindListener<Video>() {
            @Override
            public void onSuccess(List<Video> list) {
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
        if (videoEntityList != null) videoEntityList.clear();
    }

    @Override
    public String getImageUrl(int position) {
        return videoEntityList.get(position).imageUrl;
    }

    @Override
    public String getImageName(int position) {
        return videoEntityList.get(position).title;
    }


    private void handleListData(List<Video> spHotEntities) {
        if (spHotEntities == null) {
            isNoData = true;
            spRecyclerViewAdapter.removeFootView();
            return;
        } else if (spHotEntities.size() < Constant.PAGE_SIZE) {
            isNoData = true;
            spRecyclerViewAdapter.removeFootView();
            ToastUtils.showSnakbar(R.string.no_data, mRecyclerView);
        }
        videoEntityList.addAll(spHotEntities);
        spRecyclerViewAdapter.notifyDataSetChanged(videoEntityList);
    }
}
