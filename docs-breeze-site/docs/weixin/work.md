---
title: 介绍
order: 1
group:
  title: 企业微信
---

## 快速开始
1. 引入依赖
在`pom.xml`中引入以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-wechat-cp-spring-boot-starter</artifactId>
        <version>最新版本</version>
    </dependency>
 ... ...
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
```

3. 使用

```java
@Test
void getAccessToken() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    // 获取某个应用的服务类
    WxCpService wxCpService2 = WxCpConfiguration.getCpService(10068);
    assert wxCpService != null;
    String accessToken = wxCpService.getAccessToken();
}
```

## 获取access_token

> **为了安全考虑，开发者请勿将access_token返回给前端，需要开发者保存在后台，所有访问企业微信api的请求由后台发起**
>
> [获取access_token - 接口文档 - 企业微信开发者中心 (qq.com)](https://developer.work.weixin.qq.com/document/path/91039)

在大多数情况下，你是不需要显式地去刷新access token的，因为 `WxCpService`会在access token过期的时候自己刷新。

比如我们获取用户信息时微信反馈access token过期，`WxCpService`会自己刷新access token，然后再次去获取用户信息。并且会将access token更新到 `WxCpConfigStorage`里。

如果你的确需要自己手工刷新access token，则可以：

```java
wxCpService.accessTokenRefresh();
```

获得的新的access token会更新到 `WxCpConfigStorage`中。

### 代码

```java
// 获取获取access_token
WxCpConfiguration.getCpService().getAccessToken()
```

