---
title: Admin组件
order: 5
---

该组件是集成授权组件、web组件、MinIO组件、Swagger3的一个快速搭建信息系统的组件，配合前端[breeze-admin-web-arco](https://gitee.com/it-xiaofan/breeze-admin-web-arco)
使用。

## 组件说明

该组件已完成系统登录授权、系统管理（账户管理、角色管理、菜单管理、字典管理、附件管理）基础功能，解放开发者重复劳动，只需专注业务功能开发即可。

## 快速开始

1. 引入以来

在`pom.xml`中引入以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-auth-spring-boot-starter</artifactId>
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

2. 配置说明

   不需要修改任何配置即可使用

```yaml
breeze:
  admin:
    error-level: error # 记录日志到数据库的级别，error：只写入错误日志，all：写入所有到数据库(默认)
    default-password: 123456a? #默认的登录密码,默认：123456a?
    module: # 模块配置
      enable-account: true # 启用系统管理/账户管理模块，默认：true
      enable-auth: true # 启用授权登录模块，默认：true
      enable-dict: true # 启用系统管理/字典管理模块，默认：true
      enable-menu: true # 启用系统管理/菜单管理模块，默认：true
      enable-org: true # 启用系统管理/组织管理模块，默认：true
      enable-role: true # 启用系统管理/角色管理模块，默认：true
      enable-system: true # 启用系统管理模块，默认：true
    prefix:
      api: / # 全局接口前缀，适用于本组件的前缀。
      account: /sys/account # 系统管理/账户管理模块的接口，默认：/sys/account
      attachment: /sys/attachment # 系统管理/附件管理模块的接口，默认：/sys/attachment
      auth: /auth # 授权登录模块的接口，默认：/auth
      dict: /sys/dict # 系统管理/字典管理模块，默认：/sys/dict
      menu: /sys/menu # 系统管理/菜单管理模块，默认：/sys/menu
      org: /sys/org # 系统管理/组织管理模块，默认：/sys/org
      role: /sys/role # 系统管理/角色管理模块，默认：/sys/role
```

3. 使用

   启用项目，访问文档地址(/doc.html)：http://localhost:PORT/doc.html

### 附件管理

minio模块的所有功能均可食用，并且分片上传。默认接口地址分别为：

* 初始化任务：sys/attachment/upload/multipart/init

* 获取分片上传地址：sys/attachment/upload/multipart/presigned

* 合并分片：sys/attachment/upload/multipart/merge
