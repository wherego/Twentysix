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
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GirlsRecyclerViewAdapter extends RecyclerBaseAdapter<GankEntity> {

    private Context mContext;
    List<GankEntity> gankEntityList;

    public GirlsRecyclerViewAdapter(Context context, List<GankEntity> gankEntities) {
        super(context, gankEntities);
        this.mContext = context;
        this.gankEntityList = gankEntities;
    }

    @Override
    public RecyclerView.ViewHolder createViewHoldersBySelf(ViewGroup parent, int viewType) {
        return new HomeViewHolder(mInflater.inflate(R.layout.home_card_view, parent, false), mListener);
    }

    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        GankEntity newsEntity = gankEntityList.get(position);
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(newsEntity.desc);
        Picasso.with(mContext)
                .load(newsEntity.url)
                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);

    }
    public class HomeViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_shared_transition)
        protected RatioImageView iv_shared_transition;
        @Bind(R.id.tv_title)
        protected TextView tvTitle;
        @Bind(R.id.cardView)
        protected CardView cardView;
        OnRecyclerViewItemClickListener listener;

        public HomeViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            iv_shared_transition.setOriginalSize(50, 50);

            this.listener = listener;
            cardView.setOnClickListener(this);
            iv_shared_transition.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }
}
