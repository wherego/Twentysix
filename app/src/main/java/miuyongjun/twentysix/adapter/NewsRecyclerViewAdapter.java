package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miuyongjun.twentysix.bean.news.NewsEntity;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class NewsRecyclerViewAdapter extends RecyclerBaseAdapter<NewsEntity> {

    private Context mContext;
    List<NewsEntity> newsEntityList;

    public NewsRecyclerViewAdapter(Context context, List<NewsEntity> newsEntityList) {
        super(context, newsEntityList);
        this.mContext = context;
        this.newsEntityList=newsEntityList;
    }

    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        NewsEntity newsEntity = newsEntityList.get(position);
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(newsEntity.title);
        Picasso.with(mContext)
                .load(newsEntity.thumb)
                .into(homeViewHolder.iv_shared_transition);
    }

}
