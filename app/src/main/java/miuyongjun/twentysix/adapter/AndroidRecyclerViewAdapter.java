package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Article;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidRecyclerViewAdapter extends RecyclerBaseAdapter<Article> {

    private Context mContext;
    List<Article> newsEntityList;

    public AndroidRecyclerViewAdapter(Context context, List<Article> newsEntityList) {
        super(context, newsEntityList);
        this.mContext = context;
        this.newsEntityList=newsEntityList;
    }

    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        Article newsEntity = newsEntityList.get(position);
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(newsEntity.title);
        Picasso.with(mContext)
                .load(newsEntity.imageUrl)
                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);
    }

}
