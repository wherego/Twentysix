package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import miuyongjun.twentysix.R;
import miuyongjun.twentysix.bean.bmob.Article;
import miuyongjun.twentysix.common.interfaces.OnRecyclerViewItemClickListener;
import miuyongjun.twentysix.widget.RatioImageView;

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
    public RecyclerView.ViewHolder createViewHoldersBySelf(ViewGroup parent, int viewType) {
        return new AndroidViewHolder(mInflater.inflate(R.layout.android_card_view, parent, false), mListener);
    }


    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        Article articleEntity = articlesEntityList.get(position);
        AndroidViewHolder homeViewHolder = (AndroidViewHolder) holder;
        homeViewHolder.tv_author.setText(articleEntity.author);
        homeViewHolder.tvTitle.setText(articleEntity.title);
        Random rand = new Random();
        homeViewHolder.logo.setTitleText(articleEntity.author.substring(0, 1).toUpperCase());
        homeViewHolder.logo.setFillColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        Picasso.with(mContext)
                .load(articleEntity.imageUrl)
                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);
    }

    public class AndroidViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_shared_transition)
        protected RatioImageView iv_shared_transition;
        @Bind(R.id.tv_title)
        protected TextView tvTitle;
        @Bind(R.id.cardView)
        protected CardView cardView;
        @Bind(R.id.iv_logo)
        protected CircleView logo;
        @Bind(R.id.tv_author)
        protected TextView tv_author;
        OnRecyclerViewItemClickListener listener;

        public AndroidViewHolder(View convertView, OnRecyclerViewItemClickListener listener) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            iv_shared_transition.setOriginalSize(50, 20);
            this.listener = listener;
            cardView.setOnClickListener(this);
            iv_shared_transition.setOnClickListener(this);
            logo.setOnClickListener(this);
            tv_author.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, getPosition());
        }
    }


}
