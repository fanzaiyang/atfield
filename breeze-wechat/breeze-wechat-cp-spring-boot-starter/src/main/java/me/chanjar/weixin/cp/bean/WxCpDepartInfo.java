package me.chanjar.weixin.cp.bean;

import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 企业微信的部门.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpDepartInfo implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  private String id;
  private String name;
  private String enName;
  private String[] departmentLeader;
  private String parentId;
  private Long order;

  /**
   * From json wx cp depart.
   *
   * @param json the json
   * @return the wx cp depart
   */
  public static WxCpDepartInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDepartInfo.class);
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
