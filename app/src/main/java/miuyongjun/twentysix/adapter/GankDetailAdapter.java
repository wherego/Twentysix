package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.gank.GankEntity;
import miuyongjun.twentysix.common.LoadMoreRecyclerViewAdapter;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.utils.DateUtils;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDetailAdapter extends LoadMoreRecyclerViewAdapter<GankEntity> {

    private Context mContext;
    boolean canLoadMore;
    private LayoutInflater mInflater;
    public OnRecyclerViewItemClickListener mListener;
    List<GankEntity> gankEntityList;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean canLoad() {
        return canLoadMore;
    }

    public GankDetailAdapter(Context context, List<GankEntity> gankEntities, boolean canLoadMore) {
        super(context, gankEntities);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.gankEntityList = gankEntities;
        this.canLoadMore = canLoadMore;
    }

    @Override
    public RecyclerView.ViewHolder createViewHoldersBase(ViewGroup parent, int viewType) {
        return new GankViewHolder(mInflater.inflate(R.layout.gank_detail_recycler_item, parent, false), mListener);
    }

    @Override
    public void onBindViewHolderBase(RecyclerView.ViewHolder holder, int position) {
        if (gankEntityList == null || position == gankEntityList.size()) {
            return;
        }
        if (getItemViewType(position) == Normal) {
            GankEntity gankEntity = gankEntityList.get(position);
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.tv_title.setText(gankEntity.desc);
            gankViewHolder.tv_time.setText(Html.fromHtml(gankEntity.who + " " + DateUtils.toDate(gankEntity.publishedAt)));
        }
    }

    @Override
    public void notifyDataSetChangedBase(List<GankEntity> mlist) {
        if (mlist != null) gankEntityList = mlist;
        notifyDataSetChanged();
    }

    public class GankViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.tv_title)
        protected TextView tv_title;
        @Bind(R.id.tv_time)
        protected TextView tv_time;
        @Bind(R.id.contentPanel)
        protected LinearLayout contentPanel;
        OnRecyclerViewItemClickListener listener;

        public GankViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            this.listener = listener;
            contentPanel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }
}
