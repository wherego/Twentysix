package miuyongjun.twentysix.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import miuyongjun.twentysix.R;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int FootType = 1;
    private LayoutInflater mInflater;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mOriginalAdapter;

    public CommonRecyclerViewAdapter(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> mOriginalAdapter) {
        this.mOriginalAdapter = mOriginalAdapter;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FootType) {
            return new ProgressViewHolder(mInflater.inflate(R.layout.load_more, parent, false));
        } else {
            return mOriginalAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mOriginalAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mOriginalAdapter.getItemCount() ? FootType : mOriginalAdapter.getItemViewType(position);
    }
}
