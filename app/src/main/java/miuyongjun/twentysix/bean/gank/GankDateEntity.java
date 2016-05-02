package miuyongjun.twentysix.bean.gank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by miaoyongjun on 16/5/1.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class GankDateEntity {
    @SerializedName("Android")
    public List<GankEntity> androidList;

    @SerializedName("iOS")
    public List<GankEntity> iosList;

    @SerializedName("拓展资源")
    public List<GankEntity> tuozhangList;
}
