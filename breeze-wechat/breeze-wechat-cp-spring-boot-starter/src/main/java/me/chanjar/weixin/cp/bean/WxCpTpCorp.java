package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 微信部门.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpTpCorp implements Serializable {

  private static final long serialVersionUID = -5028321625140879571L;
  @SerializedName("corpid")
  private String corpId;
  @SerializedName("corp_name")
  private String corpName;
  @SerializedName("corp_full_name")
  private String corpFullName;
  @SerializedName("corp_type")
  private String corpType;
  @SerializedName("corp_square_logo_url")
  private String corpSquareLogoUrl;
  @SerializedName("corp_user_max")
  private String corpUserMax;
  @SerializedName("permanent_code")
  private String permanentCode;
  @SerializedName("auth_info")
  private String authInfo;

  /**
   * From json wx cp tp corp.
   *
   * @param json the json
   * @return the wx cp tp corp
   */
  public static WxCpTpCorp fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpCorp.class);
  }

  /**
   * To json string.
   *
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
