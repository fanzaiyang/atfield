---
title: 身份验证
order: 3
group:
  title: 企业微信
---

## 扫码授权登录

### 构造扫码登录链接

> [构造扫码登录链接 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/91019)

这个是需要前端引入官方js后构造的。你只需要与前端对接好回调地址即可。

### 获取访问用户身份

```
@Test
void getuserinfo() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpOAuth2Service oauth2Service = wxCpService.getOauth2Service();
    // 根据前台传入的授权码获取用户信息
    WxCpOauth2UserInfo userInfo = oauth2Service.getUserInfo("CODE");
}
```

## 网页授权登录

### 开始开发

> [开始开发 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/91335)
>
> 企业微信提供了OAuth的授权登录方式，可以让从企业微信终端打开的网页获取成员的身份信息，从而免去登录的环节。
> 企业应用中的URL链接（包括自定义菜单或者消息中的链接），均可通过OAuth2.0验证接口来获取成员的UserId身份信息。

### 构造网页授权链接

> [构造网页授权链接 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/91022)

```java
@Test
void authorizeUrl() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    String authorizationUrl = wxCpService.getOauth2Service().buildAuthorizationUrl("请使用urlencode对链接进行处理redirect_uri", "state", "snsapi_base");
```

### 获取访问用户身份

> [获取访问用户身份 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/91023)
>
> 该接口用于根据code获取成员信息

```java
@Test
void getuserinfo() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpOAuth2Service oauth2Service = wxCpService.getOauth2Service();
    // 根据前台传入的授权码获取用户信息
    WxCpOauth2UserInfo userInfo = oauth2Service.getUserInfo("CODE");
}
```
