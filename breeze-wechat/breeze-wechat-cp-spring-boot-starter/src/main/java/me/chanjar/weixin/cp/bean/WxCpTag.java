package me.chanjar.weixin.cp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * Created by Daniel Qian.
 *
 * @author Daniel Qian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCpTag implements Serializable {
  private static final long serialVersionUID = -7243320279646928402L;

  private String id;

  private String name;


  /**
   * From json wx cp tag.
   *
   * @param json the json
   * @return the wx cp tag
   */
  public static WxCpTag fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTag.class);
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
