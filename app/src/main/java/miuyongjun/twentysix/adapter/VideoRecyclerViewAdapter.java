package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Video;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

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
        Picasso.with(mContext)
                .load(videoEntity.imageUrl)
                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.video_image);
    }

    public class VideoViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.video_title)
        protected TextView video_title;
        @Bind(R.id.play_btn)
        ImageView play_btn;
        @Bind(R.id.video_image)
        RatioImageView video_image;
        OnRecyclerViewItemClickListener listener;

        public VideoViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            this.listener = listener;
            video_image.setOriginalSize(50, 30);
            play_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }
}
