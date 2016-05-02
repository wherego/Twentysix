package miuyongjun.twentysix.widget;

import android.support.v7.widget.GridLayoutManager;

import miuyongjun.twentysix.common.LoadMoreRecyclerViewAdapter;

/**
 * Created by miuyongjun on 16/4/30.
 */
public class RecyclerSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private LoadMoreRecyclerViewAdapter adapter;

    public RecyclerSpanSizeLookup(LoadMoreRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getSpanSize(int position) {
        switch (adapter.getItemViewType(position)) {
            case LoadMoreRecyclerViewAdapter.Normal:
                return 1;
            case LoadMoreRecyclerViewAdapter.FootType:
                return 2;
            default:
                return -1;
        }
    }
}
