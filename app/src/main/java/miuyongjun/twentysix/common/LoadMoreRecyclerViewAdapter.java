package miuyongjun.twentysix.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import miuyongjun.twentysix.R;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public abstract class LoadMoreRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int FOOT_VIEW_COUNT = 1;
    public static final int Normal = 0;
    public static final int FootType = 1;
    private int lastPosition = -1;
    private LayoutInflater mInflater;
    List<T> tList = new ArrayList<>();
    public View footView;
    Context context;

    public View getFootView() {
        return footView;
    }

    public void removeFootView() {
        FOOT_VIEW_COUNT = 0;
        notifyDataSetChanged();
    }

    public boolean canLoad() {
        return true;
    }


    public LoadMoreRecyclerViewAdapter(Context context, List<T> tList) {
        mInflater = LayoutInflater.from(context);
        this.tList = tList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FootType) {
            footView = mInflater.inflate(R.layout.load_more, parent, false);
            return new ViewHolder(footView);
        } else {
            return createViewHoldersBase(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder createViewHoldersBase(ViewGroup parent,
                                                                  int viewType);

    public abstract void onBindViewHolderBase(RecyclerView.ViewHolder holder, int position);


    public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View view) {
            super(view);
        }

    }

    public abstract void notifyDataSetChangedBase(List<T> mlist);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolderBase(holder, position);
        setAnimation(holder.itemView, position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return tList == null ? 0 : canLoad() ? tList.size() + FOOT_VIEW_COUNT : tList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return canLoad() ? FOOT_VIEW_COUNT == 1 ? position == tList.size() ? FootType : Normal : Normal : Normal;
    }

    public void notifyDataSetChanged(List<T> mlist) {
        if (mlist != null) notifyDataSetChangedBase(mlist);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        clearAnimation(holder);
    }

    public void clearAnimation(RecyclerView.ViewHolder holder)
    {
        holder.itemView.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
