package miuyongjun.twentysix.bean.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by miaoyongjun on 2016/5/4.
 * 　　　　    　┃┫┫　┃┫┫
 * 　　　　    　┗┻┛　┗┻┛
 */

public class Video extends BmobObject implements Serializable {
    public String title;
    public String describe;
    public String imageUrl;
    public String videoUrl;
    public String originalVideoLink;

}
