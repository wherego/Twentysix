package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import miuyongjun.twentysix.bean.bmob.Article;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class AndroidRecyclerViewAdapter extends RecyclerBaseAdapter<Article> {

    private Context mContext;
    List<Article> articlesEntityList;

    public AndroidRecyclerViewAdapter(Context context, List<Article> newsEntityList) {
        super(context, newsEntityList);
        this.mContext = context;
        this.articlesEntityList = newsEntityList;
    }

    @Override
    public boolean needCreateViewBySelf() {
        return true;
    }


    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        Article articleEntity = articlesEntityList.get(position);
        AndroidViewHolder homeViewHolder = (AndroidViewHolder) holder;
        homeViewHolder.tv_author.setText(articleEntity.author);
        homeViewHolder.tvTitle.setText(articleEntity.title);
        Random rand = new Random();
        homeViewHolder.logo.setTitleText(articleEntity.author.substring(0,1).toUpperCase());
        homeViewHolder.logo.setFillColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        Picasso.with(mContext)
                .load(articleEntity.imageUrl)
//                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);
    }


}
