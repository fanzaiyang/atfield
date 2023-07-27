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
public class WxCpDepart implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 部门id，32位整型，指定时必须大于1。若不填该参数，将自动生成id
   * 1~2^31 - 1之间
   */
  private Long id;
  /**
   * 部门名称。同一个层级的部门名称不能重复。长度限制为1~32个UTF-8字符，字符不能包括:*?"<>｜
   */
  private String name;
  /**
   * 英文名称。同一个层级的部门名称不能重复。需要在管理后台开启多语言支持才能生效。长度限制为1~64个字符，字符不能包括:*?"<>｜
   */
  private String enName;
  private String[] departmentLeader;
  /**
   * 父部门id，32位整型1~2^31 - 1之间
   */
  private Long parentId;
  /**
   * 在父部门中的次序值。order值大的排序靠前。有效的值范围是[0, 2^32)
   */
  private Long order;

  /**
   * From json wx cp depart.
   *
   * @param json the json
   * @return the wx cp depart
   */
  public static WxCpDepart fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDepart.class);
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
