package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miuyongjun.twentysix.bean.gank.GankEntity;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GirlsRecyclerViewAdapter extends RecyclerBaseAdapter<GankEntity> {

    private Context mContext;
    List<GankEntity> gankEntityList;

    public GirlsRecyclerViewAdapter(Context context, List<GankEntity> gankEntities) {
        super(context, gankEntities);
        this.mContext = context;
        this.gankEntityList = gankEntities;
    }

    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        GankEntity newsEntity = gankEntityList.get(position);
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(newsEntity.desc);
        Picasso.with(mContext)
                .load(newsEntity.url)
                .into(homeViewHolder.iv_shared_transition);

    }
}
