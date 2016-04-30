package miuyongjun.twentysix.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.adapter.news.NewsRecyclerViewAdapter;
import miuyongjun.twentysix.bean.news.NewsBaseEntity;
import miuyongjun.twentysix.bean.news.NewsEntity;
import miuyongjun.twentysix.common.CommonRecyclerViewAdapter;
import miuyongjun.twentysix.common.RecyclerOnScrollEndListener;
import miuyongjun.twentysix.common.retrofit.RetrofitUtil;
import rx.functions.Action1;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　 　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class NewsFragment extends Fragment {

    int pageSize = 10;
    int pageIndex = 1;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.mSwipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    CommonRecyclerViewAdapter commonRecyclerViewAdapter;
    List<NewsEntity> newsEntityList;

    public NewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getData();
    }

    private void initView() {
        newsEntityList = new ArrayList<>();
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity());
        commonRecyclerViewAdapter = new CommonRecyclerViewAdapter(getActivity(),newsRecyclerViewAdapter);
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
//        manager.setSpanSizeLookup(mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(commonRecyclerViewAdapter);
        mRecyclerView.setOnScrollListener(recyclerOnScrollEndListener);
    }

    RecyclerOnScrollEndListener recyclerOnScrollEndListener = new RecyclerOnScrollEndListener(){
        @Override
        public void onLoad(View view) {
            pageIndex++;
            getData();
        }
    };

    private void getData() {
        String json = "{'device':'android','catid':" + pageIndex + ",'pagesize':" + pageSize + ",'sid':'11142'}";
        RetrofitUtil.getNewsApi(json)
                .subscribe(new Action1<NewsBaseEntity>() {
                    @Override
                    public void call(NewsBaseEntity newsBaseEntity) {
                        newsEntityList.addAll(newsBaseEntity.getNewsEntityList());
                        newsRecyclerViewAdapter.notifyDataSetChanged(newsEntityList);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
