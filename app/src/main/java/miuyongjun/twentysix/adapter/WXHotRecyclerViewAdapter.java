package miuyongjun.twentysix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miuyongjun.twentysix.bean.wechat.WXHotEntity;

/**
 * Created by miaoyongjun on 16/5/3.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class WXHotRecyclerViewAdapter extends RecyclerBaseAdapter<WXHotEntity>{
    private Context mContext;
    List<WXHotEntity> wxHotEntityList;

    public WXHotRecyclerViewAdapter(Context context, List<WXHotEntity> wxHotEntityList) {
        super(context, wxHotEntityList);
        this.mContext = context;
        this.wxHotEntityList = wxHotEntityList;
    }


    @Override
    public void onBindViewBase(RecyclerView.ViewHolder holder, int position) {
        WXHotEntity wxHotEntity = wxHotEntityList.get(position);
        RecyclerBaseAdapter.HomeViewHolder homeViewHolder = (RecyclerBaseAdapter.HomeViewHolder) holder;
        homeViewHolder.tvTitle.setText(wxHotEntity.title);
        Picasso.with(mContext)
                .load(wxHotEntity.picUrl)
//                .placeholder(R.mipmap.default_bg)
                .into(homeViewHolder.iv_shared_transition);

    }
}
