package miuyongjun.twentysix.bean.news;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaoyongjun on 2016/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class NewsBaseEntity {
    @SerializedName("data")
    public List<NewsEntity> newsEntityList = new ArrayList<>();
}
