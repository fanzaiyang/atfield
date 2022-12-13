---
title: 回调方法
group:
  title: 企业微信
---
# 回调方法

> 用来接收微信推送到本服务的消息

## 接口一览

* WxCpContactChangeHandler
* WxCpEnterAgentHandler
* WxCpLocationHandler
  * 上报地理位置事件
* WxCpLogHandler
  * 记录所有事件的日志 （异步执行）
* WxCpMenuHandler
  * 自定义菜单事件
* WxCpMenuClickHandler
  * 点击菜单链接事件
* WxCpMsgHandler
* WxCpNullHandler
* WxCpScanHandler
  * 扫码事件
* WxCpSubscribeHandler
  * 关注事件
* WxCpUnsubscribeHandler
  * 取消关注事件

根据业务情况，实现上述接口，并交由spring管理即可。
