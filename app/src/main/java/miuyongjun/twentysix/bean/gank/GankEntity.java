package miuyongjun.twentysix.bean.gank;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class GankEntity implements Serializable{
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String desc;
    @SerializedName("publishedAt")
    public Date publishedAt;
    @SerializedName("source")
    public String source;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("used")
    public Boolean used;
    @SerializedName("who")
    public String who;

}
