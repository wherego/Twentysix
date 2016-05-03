package miuyongjun.twentysix.bean.gank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDateEntity {

    @SerializedName("福利")
    public List<GankEntity> fuliList;

    @SerializedName("Android")
    public List<GankEntity> androidList;

    @SerializedName("iOS")
    public List<GankEntity> iosList;

    @SerializedName("休息视频")
    public List<GankEntity> xiuxiList;

    @SerializedName("拓展资源")
    public List<GankEntity> tuozhangList;

    @SerializedName("前端")
    public List<GankEntity> qiandunaList;
}
