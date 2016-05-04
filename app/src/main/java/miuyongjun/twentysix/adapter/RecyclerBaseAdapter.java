package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import miuyongjun.twentysix.common.LoadMoreRecyclerViewAdapter;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public abstract class RecyclerBaseAdapter<T> extends LoadMoreRecyclerViewAdapter<T> {

    private Context mContext;
    public LayoutInflater mInflater;
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
        return createViewHoldersBySelf(parent, viewType);
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

    public abstract RecyclerView.ViewHolder createViewHoldersBySelf(ViewGroup parent,
                                                                  int viewType);

    public abstract void onBindViewBase(RecyclerView.ViewHolder holder, int position);


    @Override
    public void notifyDataSetChangedBase(List<T> mlist) {
        if (mlist != null) baseEntityList = mlist;
        notifyDataSetChanged();
    }
}
