package cn.fanzy.breeze.web.model.enums;

public enum ErrorShowType {
    /**
     * 静默，不显示
     */
    SILENT,
    /**
     * 显示为警告Message
     */
    MESSAGE_WARN,
    /**
     * 显示为错误Message
     */
    MESSAGE_ERROR,
    /**
     * 显示为弹窗警告
     */
    MODAL_WARN,
    /**
     * 显示为弹窗错误
     */
    MODAL_ERROR,
    /**
     * 显示为通知警告NOTIFICATION
     */
    NOTIFICATION_WARN,
    /**
     * 显示为通知错误NOTIFICATION
     */
    NOTIFICATION_ERROR;
}
