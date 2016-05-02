package miuyongjun.twentysix.bean.wechat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaoyongjun on 16/4/30.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */
public class WXHotBaseEntity {
    @SerializedName("newslist")
    public List<WXHotEntity> wxHotEntityList = new ArrayList<>();
}
