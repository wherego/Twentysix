package miuyongjun.twentysix.bean.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/4/30.
 */

public class NewsEntity {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("descr")
    @Expose
    private String descr;
    @SerializedName("refinfo")
    @Expose
    private String refinfo;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("viewnum")
    @Expose
    private String viewnum;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     *
     * @param descr
     * The descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     *
     * @return
     * The refinfo
     */
    public String getRefinfo() {
        return refinfo;
    }

    /**
     *
     * @param refinfo
     * The refinfo
     */
    public void setRefinfo(String refinfo) {
        this.refinfo = refinfo;
    }

    /**
     *
     * @return
     * The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     *
     * @param thumb
     * The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The viewnum
     */
    public String getViewnum() {
        return viewnum;
    }

    /**
     *
     * @param viewnum
     * The viewnum
     */
    public void setViewnum(String viewnum) {
        this.viewnum = viewnum;
    }
}
