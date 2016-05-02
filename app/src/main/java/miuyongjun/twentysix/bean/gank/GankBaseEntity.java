package miuyongjun.twentysix.bean.gank;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */

public class GankBaseEntity {
    @SerializedName("results")
    public List<GankEntity> gankEntityList = new ArrayList<>();
}
