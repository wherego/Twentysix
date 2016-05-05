package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Video;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.video.MVideoPlayer;

/**
 * Created by miaoyongjun on 16/5/3.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class VideoRecyclerViewAdapter extends RecyclerBaseAdapter<Video> {
    private Context mContext;
    List<Video> videoEntityList;

    public VideoRecyclerViewAdapter(Context context, List<Video> wxHotEntityList) {
        super(context, wxHotEntityList);
        this.mContext = context;
        this.videoEntityList = wxHotEntityList;
    }

    @Override
    public RecyclerView.ViewHolder createViewHoldersBySelf(ViewGroup parent, int viewType) {
        return new VideoViewHolder(mInflater.inflate(R.layout.video_card_view, parent, false), mListener);
    }


    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        Video videoEntity = videoEntityList.get(position);
        VideoViewHolder homeViewHolder = (VideoViewHolder) holder;
        homeViewHolder.video_title.setText(videoEntity.title);

    }

    public class VideoViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.video_title)
        protected TextView video_title;
        @Bind(R.id.cardView)
        protected CardView cardView;
        @Bind(R.id.play_btn)
        ImageView play_btn;
        @Bind(R.id.video_player)
        MVideoPlayer video_player;
        OnRecyclerViewItemClickListener listener;

        public VideoViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            this.listener = listener;
            play_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }
}
