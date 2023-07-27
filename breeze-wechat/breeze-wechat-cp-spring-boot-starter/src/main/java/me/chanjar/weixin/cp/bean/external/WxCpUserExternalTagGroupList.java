package me.chanjar.weixin.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * The type Wx cp user external tag group list.
 *
 * @author huangxm129
 */
@Getter
@Setter
public class WxCpUserExternalTagGroupList extends WxCpBaseResp {
  private static final long serialVersionUID = -3349321791821450679L;

  @SerializedName("tag_group")
  private List<TagGroup> tagGroupList;

  /**
   * The type Tag group.
   */
  @Getter
  @Setter
  public static class TagGroup implements Serializable {
    private static final long serialVersionUID = -4301684507150486556L;

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("group_name")
    private String groupName;

    @SerializedName("create_time")
    private Long createTime;

    @SerializedName("order")
    private Long order;

    @SerializedName("deleted")
    private Boolean deleted;


    @SerializedName("tag")
    private List<Tag> tag;

    /**
     * The type Tag.
     */
    @Getter
    @Setter
    public static class Tag implements Serializable {
      private static final long serialVersionUID = -4301684507150486556L;

      /**
       * 客户群ID
       */
      @SerializedName("id")
      private String id;

      @SerializedName("name")
      private String name;

      @SerializedName("create_time")
      private Long createTime;

      @SerializedName("order")
      private Long order;

      @SerializedName("deleted")
      private Boolean deleted;

    }
  }

  public String toJson() {
    return WxGsonBuilder.create().toJson(this);
  }

  /**
   * From json wx cp user external tag group list.
   *
   * @param json the json
   * @return the wx cp user external tag group list
   */
  public static WxCpUserExternalTagGroupList fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpUserExternalTagGroupList.class);
  }
}
