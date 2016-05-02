package miuyongjun.twentysix.bean.news;

import com.google.gson.annotations.SerializedName;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class NewsEntity   {

    @SerializedName("title")
    public String title;
    @SerializedName("link")
    public String link;
    @SerializedName("descr")
    public String descr;
    @SerializedName("refinfo")
    public String refinfo;
    @SerializedName("thumb")
    public String thumb;
    @SerializedName("time")
    public String time;
    @SerializedName("viewnum")
    public String viewnum;
}
