package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 外部联系人详情
 * Created by Binary Wang on 2018/9/16.
 * 参考文档：https://work.weixin.qq.com/api/doc#13878
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Getter
@Setter
public class WxCpUserExternalContactInfo implements Serializable {
  private static final long serialVersionUID = -5696099236344075582L;

  @SerializedName("external_contact")
  private ExternalContact externalContact;

  @SerializedName("follow_user")
  private List<FollowedUser> followedUsers;

  /**
   * The type External contact.
   */
  @Getter
  @Setter
  public static class ExternalContact implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("external_userid")
    private String externalUserId;

    @SerializedName("position")
    private String position;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("corp_name")
    private String corpName;

    @SerializedName("corp_full_name")
    private String corpFullName;

    @SerializedName("type")
    private Integer type;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("unionid")
    private String unionId;

    @SerializedName("external_profile")
    private ExternalProfile externalProfile;
  }

  /**
   * The type External profile.
   */
  @Setter
  @Getter
  public static class ExternalProfile implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("external_attr")
    private List<ExternalAttribute> externalAttrs;
  }

  /**
   * The type External attribute.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ExternalAttribute implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    /**
     * The type Text.
     */
    @Setter
    @Getter
    public static class Text implements Serializable {
      private static final long serialVersionUID = -5696099236344075582L;

      private String value;
    }

    /**
     * The type Web.
     */
    @Setter
    @Getter
    public static class Web implements Serializable {
      private static final long serialVersionUID = -5696099236344075582L;

      private String title;
      private String url;
    }

    /**
     * The type Mini program.
     */
    @Setter
    @Getter
    public static class MiniProgram implements Serializable {
      private static final long serialVersionUID = -5696099236344075582L;

      @SerializedName("pagepath")
      private String pagePath;
      private String appid;
      private String title;
    }

    private int type;

    private String name;

    private Text text;

    private Web web;

    @SerializedName("miniprogram")
    private MiniProgram miniProgram;
  }

  /**
   * The type Followed user.
   */
  @Setter
  @Getter
  public static class FollowedUser implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("userid")
    private String userId;
    private String remark;
    private String description;
    @SerializedName("createtime")
    private Long createTime;
    private String state;
    @SerializedName("remark_company")
    private String remarkCompany;
    @SerializedName("remark_mobiles")
    private String[] remarkMobiles;
    private Tag[] tags;
    @SerializedName("add_way")
    private Integer addWay;
    @SerializedName("oper_userid")
    private String operUserid;

  }

  /**
   * From json wx cp user external contact info.
   *
   * @param json the json
   * @return the wx cp user external contact info
   */
  public static WxCpUserExternalContactInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpUserExternalContactInfo.class);
  }

  /**
   * The type Tag.
   */
  @Setter
  @Getter
  public static class Tag implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("group_name")
    private String groupName;
    @SerializedName("tag_name")
    private String tagName;
    private int type;
  }
}
