package miuyongjun.twentysix.bean.gank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */

public class GankBaseEntity {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    private List<GankAndroidEntity> gankAndroidEntityList = new ArrayList<>();

    /**
     * @return The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * @return The results
     */
    public List<GankAndroidEntity> getResults() {
        return gankAndroidEntityList;
    }

    /**
     * @param results The results
     */
    public void setResults(List<GankAndroidEntity> results) {
        this.gankAndroidEntityList = results;
    }
}
