---
title: 素材管理
order: 5
group:
  title: 企业微信
---
# 素材管理

## 使用场景

> 在使用企业微信API接口中，往往开发者需要使用自定义的资源，比如发送本地图片消息，设置通讯录自定义头像等。
> 为了实现同一资源文件，一次上传可以多次使用，这里提供了素材管理接口：以media_id来标识资源文件，实现文件的上传与下载。

## 上传临时素材

> 素材上传得到media_id，该media_id仅三天内有效
> media_id在同一企业内应用之间可以共享

```java
@Test
void uploadTemp() throws WxErrorException, IOException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpMediaService mediaService = wxCpService.getMediaService();
    WxMediaUploadResult upload = mediaService.upload(WxConsts.MediaFileType.IMAGE, FileUtil.file(""));
    WxMediaUploadResult upload2 = mediaService.upload(WxConsts.MediaFileType.IMAGE, 
            "wework.txt","http://ssssss");
    WxMediaUploadResult upload3 = mediaService.upload(WxConsts.MediaFileType.IMAGE,
            "wework.txt", IoUtil.toStream(FileUtil.file("")));
}
```

## 上传图片

> 上传图片得到图片URL，该URL永久有效
> 返回的图片URL，仅能用于[图文消息](https://developer.work.weixin.qq.com/document/path/90256#10167/图文消息)正文中的图片展示，或者给客户发送欢迎语等；若用于非企业微信环境下的页面，图片将被屏蔽。
> 每个企业每月最多可上传3000张图片，每天最多可上传1000张图片

```java
@Test
void uploadPic() throws WxErrorException, IOException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpMediaService mediaService = wxCpService.getMediaService();
    mediaService.uploadImg(FileUtil.file(""));
}
```

## 获取临时素材

> 完全公开，media_id在同一企业内所有应用之间可以共享。

```java
@Test
void getMedia() throws WxErrorException, IOException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpMediaService mediaService = wxCpService.getMediaService();
    mediaService.download("mediaId");
}
```

## 获取高清语音素材

```java
@Test
void getMediaHd() throws WxErrorException, IOException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpMediaService mediaService = wxCpService.getMediaService();
    mediaService.getJssdkFile("mediaId");
}
```
