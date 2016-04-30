package miuyongjun.twentysix.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.news.NewsEntity;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    public OnRecyclerViewItemClickListener mListener;
    List<NewsEntity> newsEntityList;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    public NewsRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(mInflater.inflate(R.layout.home_card_view, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == newsEntityList.size()) {
            return;
        }
        NewsEntity newsEntity = newsEntityList.get(position);
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(newsEntity.getTitle());
        Glide.with(mContext)
                .load(newsEntity.getThumb())
                .centerCrop()
                .into(homeViewHolder.headView);
    }

    @Override
    public int getItemCount() {
        return newsEntityList == null ? 0 : newsEntityList.size() + 1;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.headView)
        RatioImageView headView;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        OnRecyclerViewItemClickListener listener;

        public HomeViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);

            headView.setOriginalSize(50, 50);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getPosition());
        }
    }


    public void notifyDataSetChanged(List<NewsEntity> mlist) {
        if (mlist != null) {
            newsEntityList = mlist;
        }
        super.notifyDataSetChanged();
    }
}
