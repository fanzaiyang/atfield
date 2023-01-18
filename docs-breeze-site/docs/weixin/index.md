---
title: 起步
order: 1

---

# 起步

## 介绍

微信开发 Java SDK，支持微信支付、开放平台、公众号、企业号/企业微信、小程序等的后端开发。本插件基于[WxJava](https://gitee.com/binary/weixin-java-tools)开发。

> ⚠️注意：
> 
> 为了更好的快速使用本插件，微信官方文档需要了解。

* 企业微信
  * https://developer.work.weixin.qq.com/document/path/90664
* 微信公众号
  * https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Overview.html
* 微信小程序
  * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/
* 微信支付
  * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/wechatpay/wechatpay-1.shtml

## 特性

* 多账号统一管理
* 开箱即用的高质量组件
* 简化调用

## 快速开始

1. 根据你的业务要求，引入不同的组件的依赖。

```xml
<dependencies>
    <!-- 企业微信 -->
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-wechat-cp-spring-boot-starter</artifactId>
        <version>最新版本</version>
    </dependency>
    <!-- 小程序 -->
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-wechat-ma-spring-boot-starter</artifactId>
        <version>最新版本</version>
    </dependency>
    <!-- 公众号 -->
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-wechat-mp-spring-boot-starter</artifactId>
        <version>最新版本</version>
    </dependency>
    <!-- 企业支付 -->
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-wechat-pay-spring-boot-starter</artifactId>
        <version>最新版本</version>
    </dependency>
</dependencies>
<!-- maven私服 -->
<repositories>
    <repository>
        <id>yinfengMaven</id>
        <name>nexus repository</name>
        <url>http://maven.yinfengnet.com/repository/maven-public/</url>
    </repository>
</repositories>
```

2. 添加配置
   
   > **注意：**
   > 
   > 微信组件需要添加配置文件才能使用。

```yaml
breeze:
  wechat:
    # 企业微信相关配置
    cp:
      corp-id: 1234 #设置企业微信的corpId，必填项。
      app-configs:
        # 第一个应用
        - agent-id: 1001 #应用ID，必填项。
          secret: abc #应用密钥，必填项。
          token: token #设置企业微信应用的token,非必填。
          aes-key: ase-key #设置企业微信应用的EncodingAESKey,非必填。
        # 第而个应用
        - agent-id: 1002 #应用ID，必填项。
          secret: abc #应用密钥，必填项。
          token: token #设置企业微信应用的token,非必填。
          aes-key: ase-key #设置企业微信应用的EncodingAESKey,非必填。
    ma:
      app-configs: 
        - app-id: app_id #设置微信小程序的appid.必填项。
          secret: secret #设置微信小程序的Secret.必填项。
          token: token #非必填。
          aes-key: aes-key #非必填。
          msg-data-format: JSON #消息格式，XML或者JSON.非必填。
    mp:
      app-configs: 
        - app-id: app_id #设置微信公众号的appid.必填项。
          secret: secret #设置微信公众号的Secret.必填项。
          token: token #非必填。
          aes-key: aes-key #非必填。
    pay:
      app-id: app_id #设置微信支付的appid
      mch-id: mch_id #微信支付商户号
      mch-key: mch-key #微信支付商户密钥
      sub-app-id: sub_app_id #服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
      sub-mch-id: sub_mch_id #服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
      key-path: key_path #apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
      use-sandbox-env: false #以指定是否使用沙箱环境,默认false
```
