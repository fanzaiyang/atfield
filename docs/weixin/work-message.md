---
title: 消息推送
order: 2
group:
  title: 企业微信
toc: content
---

# 消息推送

> [发送应用消息 - 接口文档 - 企业微信开发者中心 (qq.com)](https://developer.work.weixin.qq.com/document/path/90236)
>
> 应用支持推送文本、图片、视频、文件、图文等类型。
>
> 详细的请求参数见官方的文档。

## 文本消息

```java
@Test
void text() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 统一消息处理类
    WxCpMessageService messageService = wxCpService.getMessageService();
    WxCpMessage message=WxCpMessage.TEXT()
            .toUser("").content("内容")
            .build();
    WxCpMessageSendResult send = messageService.send(message);
}
```

## 图片消息

```java
WxCpMessage message=WxCpMessage.IMAGE()
            .toUser("").mediaId("mediaId")
            .build();
```

## 语音消息

```java
WxCpMessage message=WxCpMessage.VOICE()
             .toUser("").mediaId("")
             .build();
```

## 视频消息

```java
WxCpMessage message=WxCpMessage.VIDEO()
            .toUser("").mediaId("")
            .build();
```

## 文件消息

```java
 WxCpMessage message=WxCpMessage.FILE()
            .toUser("").mediaId("")
            .build();
```

## 文本卡片消息

```java
WxCpMessage message=WxCpMessage.TEXTCARD()
        .toUser("").description("")
        .build();
```

## 图文消息

```
WxCpMessage message=WxCpMessage.NEWS()
        .toUser("")
        .build();
```

## 图文消息（mpnews）

> mpnews类型的图文消息，跟普通的图文消息一致，唯一的差异是图文内容存储在企业微信。
> 多次发送mpnews，会被认为是不同的图文，阅读、点赞的统计会被分开计算。

```java
WxCpMessage message=WxCpMessage.MPNEWS()
        .toUser("")
        .build();
```

## markdown消息

> 目前仅支持[markdown语法的子集](https://developer.work.weixin.qq.com/document/path/90236#10167/支持的markdown语法)
> 微工作台（原企业号）不支持展示markdown消息

```java
WxCpMessage message=WxCpMessage.MARKDOWN()
        .toUser("").content("内容")
        .build();
```

## 小程序通知消息

> 小程序通知消息只允许绑定了小程序的应用发送，~~之前，消息会通过统一的会话【小程序通知】发送给用户~~。
> 从2019年6月28日起，用户收到的小程序通知会出现在各个独立的应用中。
> 不支持@all全员发送

```java
WxCpMessage message=WxCpMessage.newMiniProgramNoticeBuilder()
        .toUser("")
        .build();
```

## 模板卡片消息

> 投票选择型和多项选择型卡片仅企业微信3.1.12及以上版本支持
> 文本通知型、图文展示型和按钮交互型三种卡片仅企业微信3.1.6及以上版本支持（但附件下载功能仍需更新至3.1.12）

### 文本通知型

```java
WxCpMessage message=WxCpMessage.TEMPLATECARD()
        .card_type("text_notice")
        .toUser("")
        .build();
```

### 图文展示型

```
WxCpMessage message=WxCpMessage.TEMPLATECARD()
        .card_type("news_notice")
        .toUser("")
        .build();
```

### 按钮交互型

```
WxCpMessage message=WxCpMessage.TEMPLATECARD()
        .card_type("button_interaction")
        .toUser("")
        .build();
```

### 投票选择型

```
WxCpMessage message=WxCpMessage.TEMPLATECARD()
        .card_type("vote_interaction")
        .toUser("")
        .build();
```

### 多项选择型

```
WxCpMessage message=WxCpMessage.TEMPLATECARD()
        .card_type("multiple_interaction")
        .toUser("")
        .build();
```
