package me.chanjar.weixin.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.cp.bean.external.moment.VisibleRange;
import me.chanjar.weixin.cp.bean.external.msg.Attachment;
import me.chanjar.weixin.cp.bean.external.msg.Text;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 企业发表内容到客户的朋友圈 创建发表任务
 *
 * @author leiin  created on  2021-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxCpAddMomentTask implements Serializable {
  @SerializedName("visible_range")
  private VisibleRange visibleRange;

  private Text text;

  private List<Attachment> attachments;

  /**
   * To json string.
   *
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }
}
