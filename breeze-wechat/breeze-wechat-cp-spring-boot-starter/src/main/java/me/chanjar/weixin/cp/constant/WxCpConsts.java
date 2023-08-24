package me.chanjar.weixin.cp.constant;

import lombok.experimental.UtilityClass;

/**
 * <pre>
 * 企业微信常量
 * Created by Binary Wang on 2018/8/25.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@UtilityClass
public class WxCpConsts {
  /**
   * 企业微信端推送过来的事件类型.
   * 参考文档：https://work.weixin.qq.com/api/doc#12974
   */
  @UtilityClass
  public static class EventType {
    /**
     * 成员关注事件.
     */
    public static final String SUBSCRIBE = "subscribe";

    /**
     * 成员取消关注事件.
     */
    public static final String UNSUBSCRIBE = "unsubscribe";

    /**
     * 进入应用事件.
     */
    public static final String ENTER_AGENT = "enter_agent";

    /**
     * 上报地理位置.
     */
    public static final String LOCATION = "LOCATION";

    /**
     * 异步任务完成事件推送.
     */
    public static final String BATCH_JOB_RESULT = "batch_job_result";

    /**
     * 企业微信通讯录变更事件.
     */
    public static final String CHANGE_CONTACT = "change_contact";

    /**
     * 点击菜单拉取消息的事件推送.
     */
    public static final String CLICK = "click";

    /**
     * 点击菜单跳转链接的事件推送.
     */
    public static final String VIEW = "view";

    /**
     * 扫码推事件的事件推送.
     */
    public static final String SCANCODE_PUSH = "scancode_push";

    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件推送.
     */
    public static final String SCANCODE_WAITMSG = "scancode_waitmsg";

    /**
     * 弹出系统拍照发图的事件推送.
     */
    public static final String PIC_SYSPHOTO = "pic_sysphoto";

    /**
     * 弹出拍照或者相册发图的事件推送.
     */
    public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

    /**
     * 弹出微信相册发图器的事件推送.
     */
    public static final String PIC_WEIXIN = "pic_weixin";

    /**
     * 弹出地理位置选择器的事件推送.
     */
    public static final String LOCATION_SELECT = "location_select";

    /**
     * 任务卡片事件推送.
     */
    public static final String TASKCARD_CLICK = "taskcard_click";

    /**
     * 企业成员添加外部联系人事件推送 & 会话存档客户同意进行聊天内容存档事件回调事件
     */
    public static final String CHANGE_EXTERNAL_CONTACT = "change_external_contact";

    /**
     * 客户群事件推送
     */
    public static final String CHANGE_EXTERNAL_CHAT = "change_external_chat";

    /**
     * 企业客户标签事件推送
     */
    public static final String CHANGE_EXTERNAL_TAG = "change_external_tag";

    /**
     * 企业微信审批事件推送（自建应用审批）
     * https://developer.work.weixin.qq.com/document/path/90269
     */
    public static final String OPEN_APPROVAL_CHANGE = "open_approval_change";

    /**
     * 企业微信审批事件推送（系统审批）
     */
    public static final String SYS_APPROVAL_CHANGE = "sys_approval_change";

    /**
     * 修改日历事件
     */
    public static final String MODIFY_CALENDAR = "modify_calendar";

    /**
     * 删除日历事件
     */
    public static final String DELETE_CALENDAR = "delete_calendar";

    /**
     * 添加日程事件
     */
    public static final String ADD_SCHEDULE = "add_schedule";

    /**
     * 修改日程事件
     */
    public static final String MODIFY_SCHEDULE = "modify_schedule";

    /**
     * 删除日程事件
     */
    public static final String DELETE_SCHEDULE = "delete_schedule";

    /**
     * 家校通讯录事件
     */
    public static final String CHANGE_SCHOOL_CONTACT = "change_school_contact";

    /**
     * 产生会话回调事件
     */
    public static final String MSGAUDIT_NOTIFY = "msgaudit_notify";

    /**
     * 直播回调事件
     */
    public static final String LIVING_STATUS_CHANGE = "living_status_change";

  }

  /**
   * 会话存档事件CHANGE_TYPE
   * https://developer.work.weixin.qq.com/document/path/92005
   */
  @UtilityClass
  public static class MsgAuditChangeType {

    /**
     * The constant MSG_AUDIT_APPROVED.
     */
    public static final String MSG_AUDIT_APPROVED = "msg_audit_approved";

  }

  /**
   * 会话存档媒体类型
   * https://developer.work.weixin.qq.com/document/path/91774
   */
  @UtilityClass
  public static class MsgAuditMediaType {

    /**
     * 图片
     */
    public static final String IMAGE = "image";

    /**
     * 语音
     */
    public static final String VOICE = "voice";

    /**
     * 视频
     */
    public static final String VIDEO = "video";

    /**
     * 表情
     */
    public static final String EMOTION = "emotion";

    /**
     * 文件
     */
    public static final String FILE = "file";

    /**
     * 音频存档消息
     */
    public static final String MEETING_VOICE_CALL = "meeting_voice_call";

    /**
     * 音频共享文档消息
     */
    public static final String VOIP_DOC_SHARE = "voip_doc_share";

    @UtilityClass
    public static class MsgAuditSuffix {

      public static final String JPG = ".jpg";
      public static final String PNG = ".png";
      public static final String GIF = ".gif";
      public static final String MP4 = ".mp4";
      public static final String AMR = ".amr";

    }

  }

  /**
   * 家校通讯录变更事件CHANGE_TYPE
   */
  @UtilityClass
  public static class SchoolContactChangeType {

    /**
     * 部门变更事件
     * https://developer.work.weixin.qq.com/document/path/92052
     */
    public static final String CREATE_DEPARTMENT = "create_department";
    /**
     * The constant UPDATE_DEPARTMENT.
     */
    public static final String UPDATE_DEPARTMENT = "update_department";
    /**
     * The constant DELETE_DEPARTMENT.
     */
    public static final String DELETE_DEPARTMENT = "delete_department";

    /**
     * 成员变更事件
     * https://developer.work.weixin.qq.com/document/path/92032
     */
    public static final String CREATE_STUDENT = "create_student";
    /**
     * The constant UPDATE_STUDENT.
     */
    public static final String UPDATE_STUDENT = "update_student";
    /**
     * The constant DELETE_STUDENT.
     */
    public static final String DELETE_STUDENT = "delete_student";
    /**
     * The constant CREATE_PARENT.
     */
    public static final String CREATE_PARENT = "create_parent";
    /**
     * The constant UPDATE_PARENT.
     */
    public static final String UPDATE_PARENT = "update_parent";
    /**
     * The constant DELETE_PARENT.
     */
    public static final String DELETE_PARENT = "delete_parent";
    /**
     * The constant SUBSCRIBE.
     */
    public static final String SUBSCRIBE = "subscribe";
    /**
     * The constant UNSUBSCRIBE.
     */
    public static final String UNSUBSCRIBE = "unsubscribe";

  }

  /**
   * 企业外部联系人变更事件的CHANGE_TYPE
   */
  @UtilityClass
  public static class ExternalContactChangeType {
    /**
     * 新增外部联系人
     */
    public static final String ADD_EXTERNAL_CONTACT = "add_external_contact";
    /**
     * 编辑外部联系人
     */
    public static final String EDIT_EXTERNAL_CONTACT = "edit_external_contact";
    /**
     * 删除外部联系人
     */
    public static final String DEL_EXTERNAL_CONTACT = "del_external_contact";

    /**
     * 外部联系人免验证添加成员事件
     */
    public static final String ADD_HALF_EXTERNAL_CONTACT = "add_half_external_contact";
    /**
     * 删除跟进成员事件
     */
    public static final String DEL_FOLLOW_USER = "del_follow_user";
    /**
     * 客户接替失败事件
     */
    public static final String TRANSFER_FAIL = "transfer_fail";

    /**
     * The type External contact transfer fail reason.
     */
    @UtilityClass
    public static class ExternalContactTransferFailReason {
      /**
       * 客户拒绝
       */
      public static final String CUSTOMER_REFUSED = "customer_refused";
      /**
       * 接替成员的客户数达到上限
       */
      public static final String CUSTOMER_LIMIT_EXCEED = "customer_limit_exceed";
    }
  }

  /**
   * The type External chat change type.
   */
  @UtilityClass
  public static class ExternalChatChangeType {
    /**
     * 客户群变更事件
     */
    public static final String CREATE = "create";
    /**
     * 客户群变更事件
     */
    public static final String UPDATE = "update";
    /**
     * 客户群解散事件
     */
    public static final String DISMISS = "dismiss";

    /**
     * The type External chat update detail.
     */
    @UtilityClass
    public static class ExternalChatUpdateDetail {
      /**
       * 成员入群
       */
      public static final String ADD_MEMBER = "add_member";
      /**
       * 成员退群
       */
      public static final String DEL_MEMBER = "del_member";
      /**
       * 群主变更
       */
      public static final String CHANGE_OWNER = "change_owner";
      /**
       * 群名变更
       */
      public static final String CHANGE_NAME = "change_name";
      /**
       * 群公告变更
       */
      public static final String CHANGE_NOTICE = "change_notice";
    }
  }

  /**
   * The type External tag change type.
   */
  @UtilityClass
  public static class ExternalTagChangeType {

    /**
     * 创建企业客户标签
     */
    public static final String CREATE = "create";
    /**
     * 变更企业客户标签
     */
    public static final String UPDATE = "update";
    /**
     * 删除企业客户标签
     */
    public static final String DELETE = "delete";
    /**
     * 重排企业客户标签
     */
    public static final String SHUFFLE = "shuffle";
  }

  /**
   * The type Tage type.
   */
  @UtilityClass
  public static class TageType {
    /**
     * 标签
     */
    public static final String TAG = "tag";
    /**
     * 标签组
     */
    public static final String TAG_GROUP = "tag_group";
  }


  /**
   * 企业微信通讯录变更事件.
   */
  @UtilityClass
  public static class ContactChangeType {
    /**
     * 新增成员事件.
     */
    public static final String CREATE_USER = "create_user";

    /**
     * 更新成员事件.
     */
    public static final String UPDATE_USER = "update_user";

    /**
     * 删除成员事件.
     */
    public static final String DELETE_USER = "delete_user";

    /**
     * 新增部门事件.
     */
    public static final String CREATE_PARTY = "create_party";

    /**
     * 更新部门事件.
     */
    public static final String UPDATE_PARTY = "update_party";

    /**
     * 删除部门事件.
     */
    public static final String DELETE_PARTY = "delete_party";

    /**
     * 标签成员变更事件.
     */
    public static final String UPDATE_TAG = "update_tag";

  }

  /**
   * 互联企业发送应用消息的消息类型.
   */
  @UtilityClass
  public static class LinkedCorpMsgType {
    /**
     * 文本消息.
     */
    public static final String TEXT = "text";
    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";
    /**
     * 视频消息.
     */
    public static final String VIDEO = "video";
    /**
     * 图文消息（点击跳转到外链）.
     */
    public static final String NEWS = "news";
    /**
     * 图文消息（点击跳转到图文消息页面）.
     */
    public static final String MPNEWS = "mpnews";
    /**
     * markdown消息.
     * （目前仅支持markdown语法的子集，微工作台（原企业号）不支持展示markdown消息）
     */
    public static final String MARKDOWN = "markdown";
    /**
     * 发送文件.
     */
    public static final String FILE = "file";
    /**
     * 文本卡片消息.
     */
    public static final String TEXTCARD = "textcard";

    /**
     * 小程序通知消息.
     */
    public static final String MINIPROGRAM_NOTICE = "miniprogram_notice";
  }

  /**
   * 群机器人的消息类型.
   */
  @UtilityClass
  public static class GroupRobotMsgType {
    /**
     * 文本消息.
     */
    public static final String TEXT = "text";

    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";

    /**
     * markdown消息.
     */
    public static final String MARKDOWN = "markdown";

    /**
     * 图文消息（点击跳转到外链）.
     */
    public static final String NEWS = "news";

    /**
     * 文件类型消息.
     */
    public static final String FILE = "file";

    /**
     * 模版类型消息.
     */
    public static final String TEMPLATE_CARD = "template_card";

  }

  /**
   * 应用推送消息的消息类型.
   */
  @UtilityClass
  public static class AppChatMsgType {
    /**
     * 文本消息.
     */
    public static final String TEXT = "text";
    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";
    /**
     * 语音消息.
     */
    public static final String VOICE = "voice";
    /**
     * 视频消息.
     */
    public static final String VIDEO = "video";
    /**
     * 发送文件（CP专用）.
     */
    public static final String FILE = "file";
    /**
     * 文本卡片消息（CP专用）.
     */
    public static final String TEXTCARD = "textcard";
    /**
     * 图文消息（点击跳转到外链）.
     */
    public static final String NEWS = "news";
    /**
     * 图文消息（点击跳转到图文消息页面）.
     */
    public static final String MPNEWS = "mpnews";
    /**
     * markdown消息.
     */
    public static final String MARKDOWN = "markdown";
  }

  /**
   * The type Work bench type.
   */
  @UtilityClass
  public static class WorkBenchType {
    /**
     * The constant KEYDATA.
     */
    /*
     * 关键数据型
     * */
    public static final String KEYDATA = "keydata";
    /**
     * The constant IMAGE.
     */
    /*
     * 图片型
     * */
    public static final String IMAGE = "image";
    /**
     * The constant LIST.
     */
    /*
     * 列表型
     * */
    public static final String LIST = "list";
    /**
     * The constant WEBVIEW.
     */
    /*
     * webview型
     * */
    public static final String WEBVIEW = "webview";
  }

  /**
   * The type Welcome msg type.
   */
  @UtilityClass
  public static class WelcomeMsgType {
    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";
    /**
     * 图文消息.
     */
    public static final String LINK = "link";
    /**
     * 视频消息.
     */
    public static final String VIDEO = "video";
    /**
     * 小程序消息.
     */
    public static final String MINIPROGRAM = "miniprogram";

    /**
     * 文件消息.
     */
    public static final String FILE = "file";
  }

  /**
   * The type Product attachment type.
   */
  @UtilityClass
  public static class ProductAttachmentType {

    /**
     * 图片消息.
     */
    public static final String IMAGE = "image";

  }

}
