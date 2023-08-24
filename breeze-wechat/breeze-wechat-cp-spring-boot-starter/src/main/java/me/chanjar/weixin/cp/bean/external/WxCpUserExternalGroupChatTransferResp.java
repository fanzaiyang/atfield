package me.chanjar.weixin.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.bean.WxCpBaseResp;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

/**
 * 分配离职成员的客户群结果
 *
 * @author pg  created on  2021年6月21日
 */
@Getter
@Setter
public class WxCpUserExternalGroupChatTransferResp extends WxCpBaseResp {
  private static final long serialVersionUID = -943124579487821819L;
  /**
   * 没有成功继承的群列表
   */
  @SerializedName("failed_chat_list")
  private List<GroupChatFailedTransfer> failedChatList;

  /**
   * From json wx cp user external group chat transfer resp.
   *
   * @param json the json
   * @return the wx cp user external group chat transfer resp
   */
  public static WxCpUserExternalGroupChatTransferResp fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpUserExternalGroupChatTransferResp.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  /**
   * The type Group chat failed transfer.
   */
  @Getter
  @Setter
  public static class GroupChatFailedTransfer extends WxCpBaseResp {
    private static final long serialVersionUID = -5836775099634587239L;
    /**
     * 没能成功继承的群ID
     */
    @SerializedName("chat_id")
    private String chatId;

    /**
     * From json wx cp user external group chat transfer resp . group chat failed transfer.
     *
     * @param json the json
     * @return the wx cp user external group chat transfer resp . group chat failed transfer
     */
    public static GroupChatFailedTransfer fromJson(String json) {
      return WxCpGsonBuilder.create().fromJson(json,
        GroupChatFailedTransfer.class);
    }

    public String toJson() {
      return WxCpGsonBuilder.create().toJson(this);
    }
  }
}
