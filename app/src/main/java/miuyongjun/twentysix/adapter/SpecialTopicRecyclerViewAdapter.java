package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.SpecialTopic;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 16/5/3.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class SpecialTopicRecyclerViewAdapter extends RecyclerBaseAdapter<SpecialTopic> {
    private Context mContext;
    List<SpecialTopic> wxHotEntityList;

    public SpecialTopicRecyclerViewAdapter(Context context, List<SpecialTopic> wxHotEntityList) {
        super(context, wxHotEntityList);
        this.mContext = context;
        this.wxHotEntityList = wxHotEntityList;
    }

    @Override
    public RecyclerView.ViewHolder createViewHoldersBySelf(ViewGroup parent, int viewType) {
        return new AndroidViewHolder(mInflater.inflate(R.layout.special_topic_card_view, parent, false), mListener);
    }


    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        SpecialTopic wxHotEntity = wxHotEntityList.get(position);
        AndroidViewHolder homeViewHolder = (AndroidViewHolder) holder;
        homeViewHolder.tvTitle.setText(wxHotEntity.title);
        Picasso.with(mContext)
                .load(wxHotEntity.imageUrl)
                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);

    }

    public class AndroidViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_shared_transition)
        protected RatioImageView iv_shared_transition;
        @Bind(R.id.tv_title)
        protected TextView tvTitle;
        @Bind(R.id.cardView)
        protected CardView cardView;
        OnRecyclerViewItemClickListener listener;

        public AndroidViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            iv_shared_transition.setOriginalSize(50, 30);
            this.listener = listener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }
}
