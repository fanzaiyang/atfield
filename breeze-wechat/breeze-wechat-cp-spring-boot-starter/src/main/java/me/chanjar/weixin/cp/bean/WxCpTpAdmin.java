package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

/**
 * 应用的管理员
 *
 * @author huangxiaoming
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxCpTpAdmin extends WxCpBaseResp {
  private static final long serialVersionUID = -5028321625140879571L;

  @SerializedName("admin")
  private List<Admin> admin;

  /**
   * The type Admin.
   */
  @Getter
  @Setter
  public static class Admin extends WxCpBaseResp {
    private static final long serialVersionUID = -5028321625140879571L;

    @SerializedName("userid")
    private String userId;

    @SerializedName("open_userid")
    private String openUserId;

    @SerializedName("auth_type")
    private Integer authType;

    public String toJson() {
      return WxGsonBuilder.create().toJson(this);
    }
  }

  /**
   * From json wx cp tp admin.
   *
   * @param json the json
   * @return the wx cp tp admin
   */
  public static WxCpTpAdmin fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpAdmin.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
