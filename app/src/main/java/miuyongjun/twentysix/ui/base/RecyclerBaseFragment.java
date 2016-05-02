package miuyongjun.twentysix.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.common.RecyclerOnScrollEndListener;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.ui.activity.PictureActivity;
import miuyongjun.twentysix.utils.ToastUtils;
import miuyongjun.twentysix.utils.UIUtils;
import miuyongjun.twentysix.widget.RatioImageView;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public abstract class RecyclerBaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {
    protected int pageIndex = 1;
    @Bind(R.id.mRecyclerView)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.mSwipeLayout)
    protected SwipeRefreshLayout mSwipeLayout;
    protected boolean isNoData = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        onViewCreatedBase();
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void initView() {
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeLayout.setOnRefreshListener(this);
        initAdapter();
        mRecyclerView.setOnScrollListener(recyclerOnScrollEndListener);
    }

    RecyclerOnScrollEndListener recyclerOnScrollEndListener = new RecyclerOnScrollEndListener() {
        @Override
        public void onLoad(View view) {
            if (!isNoData) {
                pageIndex++;
                getData();
            }
        }
    };

    public abstract void onViewCreatedBase();

    public abstract void getData();

    public abstract void cleanList();

    public abstract void initAdapter();

    public abstract void cardViewClick(View v, int position);

    public String getImageUrl(int position) {
        return "";
    }

    public String getImageName(int position) {
        return "";
    }

    @Override
    public void onItemClick(View v, int position) {
        if (v instanceof RatioImageView) {
            Intent intent = new Intent(getActivity(), PictureActivity.class);
            intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, getImageUrl(position));
            intent.putExtra(PictureActivity.EXTRA_IMAGE_NAME, getImageName(position));
            UIUtils.intentWithTransition(getActivity(), intent, v);
        } else
            cardViewClick(v, position);
    }

    public void onError(Throwable throwable) {
        mSwipeLayout.setRefreshing(false);
        ToastUtils.showSnakbarErrorWithCallBack(throwable, mRecyclerView, this::onRefresh);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        isNoData = false;
        pageIndex = 1;
        getData();
        cleanList();
    }
}
