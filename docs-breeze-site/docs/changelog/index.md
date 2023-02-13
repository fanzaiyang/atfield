---
title: 更新日志
---

遵循 [Semantic Versioning 2.0.0](https://semver.org/lang/zh-CN/) 语义化版本规范。

## 发布周期

修订版本号：进行日常 bugfix 更新。（如果有紧急的 bugfix，则任何时候都可发布）
次版本号：发布一个带有新特性的向下兼容的版本。
主版本号：含有破坏性更新和新特性，不在发布周期内。

## 最新版本

![Maven Central](https://img.shields.io/maven-central/v/cn.fanzy.breeze/breeze-spring-cloud?style=for-the-badge.png)
## v2.1.5

### 🐛问题修复

* web组件，修复开启json处理后时间未格式化问题。



## v2.1.4

### 🐛问题修复

* admin组件，修复接口地址404问题。

* minio组件，优化接口返回数据。

## v2.1.3

* 分片上传初始化接口添加秒传响应对象
  
  * 方便前端拿到已上传的文件信息，方便回传后台。

## v2.1.2

- 修复分片上传合并方法未更新结束时间和花费时间两个字段。

## v2.1.1

* 修复项目build方式。

## v2.1.0

* 🎉 新增基于MinIO的分片上传、断点续传、秒传功能组件。涉及以下组件。🐶
  
  * breeze-minio-spring-boot-starter
  
  * breeze-admin-spring-boot-starter

* 🎉 新增JSON消息空处理，null转换为对应类型返回到前端。
  
  * breeze-web-spring-boot-starter

## v2.0.3

* 修复`SpringUtils`获取请求参数的方法，在获取Post Json List时异常。

* 升级MinIO的SDK版本到`8.4.6`。

## v2.0.2

- 修复admin组件，通过配置文件配置接口前缀无效问题。

## v2.0.1

* 升级依赖：SpringBoot 2.7.7

* 移除自定义异常页面

* redisson降级到3.8.0，支持springboot2.x

* 修复Admin组件接口404

## v2.0.0

升级基础依赖版本，面向SpringBoot 3.x。

### 升级基础核心依赖

* SpringBoot `2.7.4`

* SpringCloud `2021.0.5`

### 新增新特性

* `@LockDistributed`基于`redisson`的分布式锁。

* `@RateLimit`基于`redisson`的接口限流。

* 使用`OpenAPI3`替代`Swagger2`。

## v1.0.0

`2022-12-12`

**微风Cloud**是基于SpringCloud开发的，一系列实用微服务业务组件库。具有以下特性：

* 开箱即用：开发者可以自由选择仅仅使用到功能，关闭不需要的功能。

* 简单配置：在开启组件功能后，无需进行复杂配置即可使用组件的基本功能。

* 个性定制：每个组件提供大量额外配置，自定义组件复杂功能。

* 高级扩展：每个组件都可以被重写，已实现个性化需求。
