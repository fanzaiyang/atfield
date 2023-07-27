package me.chanjar.weixin.cp.constant;

import lombok.experimental.UtilityClass;

/**
 * The type Wx cp tp consts.
 */
public class WxCpTpConsts {


  /**
   * The type Info type.
   */
  @UtilityClass
  public static class InfoType {
    /**
     * 推送更新suite_ticket
     */
    public static final String SUITE_TICKET = "suite_ticket";

    /**
     * 从企业微信应用市场发起授权时,授权成功通知
     */
    public static final String CREATE_AUTH = "create_auth";

    /**
     * 从企业微信应用市场发起授权时,变更授权通知
     */
    public static final String CHANGE_AUTH = "change_auth";

    /**
     * 从企业微信应用市场发起授权时,取消授权通知
     */
    public static final String CANCEL_AUTH = "cancel_auth";

    /**
     * 企业互联共享应用事件回调
     */
    public static final String SHARE_AGENT_CHANGE = "share_agent_change";

    /**
     * 重置永久授权码通知
     */
    public static final String RESET_PERMANENT_CODE = "reset_permanent_code";

    /**
     * 应用管理员变更通知
     */
    public static final String CHANGE_APP_ADMIN = "change_app_admin";

    /**
     * 通讯录变更通知
     */
    public static final String CHANGE_CONTACT = "change_contact";

    /**
     * 用户进行企业微信的注册，注册完成回调通知
     */
    public static final String REGISTER_CORP = "register_corp";

    /**
     * 异步任务回调通知
     */
    public static final String BATCH_JOB_RESULT = "batch_job_result";

    /**
     * 外部联系人变更通知
     */
    public static final String CHANGE_EXTERNAL_CONTACT = "change_external_contact";

    /**
     * 下单成功通知
     */
    public static final String OPEN_ORDER = "open_order";

    /**
     * 改单通知
     */
    public static final String CHANGE_ORDER = "change_order";

    /**
     * 支付成功通知
     */
    public static final String PAY_FOR_APP_SUCCESS = "pay_for_app_success";

    /**
     * 退款通知
     */
    public static final String REFUND = "refund";

    /**
     * 付费版本变更通知
     */
    public static final String CHANGE_EDITION = "change_editon";


    /**
     * 接口许可失效通知
     */
    public static final String UNLICENSED_NOTIFY = "unlicensed_notify";

    /**
     * 支付成功通知
     */
    public static final String LICENSE_PAY_SUCCESS = "license_pay_success";

    /**
     * 退款结果通知
     */
    public static final String LICENSE_REFUND = "license_refund";


  }

}
