package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.common.LoadMoreRecyclerViewAdapter;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public abstract class RecyclerBaseAdapter<T> extends LoadMoreRecyclerViewAdapter<T> {

    private Context mContext;
    private LayoutInflater mInflater;
    public OnRecyclerViewItemClickListener mListener;
    List<T> baseEntityList;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }


    public RecyclerBaseAdapter(Context context, List<T> newsEntityList) {
        super(context, newsEntityList);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createViewHoldersBase(ViewGroup parent, int viewType) {
        return new HomeViewHolder(mInflater.inflate(R.layout.home_card_view, parent, false), mListener);
    }

    @Override
    public void onBindViewHolderBase(RecyclerView.ViewHolder holder, int position) {
        if (baseEntityList == null || position == baseEntityList.size()) {
            return;
        }
        if (getItemViewType(position) == Normal) {
            onBindViewBase(holder, position);
        }
    }

    public abstract void onBindViewBase(RecyclerView.ViewHolder holder, int position);


    @Override
    public void notifyDataSetChangedBase(List<T> mlist) {
        if (mlist != null) baseEntityList = mlist;
        notifyDataSetChanged();
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
