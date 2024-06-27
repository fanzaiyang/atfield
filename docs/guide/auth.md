---
title: 授权组件
order: 3
---

该组件集成了[SaToken 1.33.0](https://gitee.com/dromara/sa-token)一个轻量级 Java 权限认证框架，让鉴权变得简单、优雅！该组件使用简单的API即可实现强大的功能。

## 组件说明

组件针对业务常见场景，对SaToken进行了部分业务封装，仅需简单配置即可实现常用的场景。默认实现了基于路由和注解的鉴权，只需要通过简单配置文件即可自定义使用。

## 快速开始

1. 引入依赖

在`pom.xml`中引入以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-auth-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

2. 修改配置

> ⚠️**注意：**
>
> 该组件可以在未任何配置的情况下使用，默认开启了[注解](https://sa-token.cc/doc.html#/use/at-check)
> 和[路由](https://sa-token.cc/doc.html#/use/route-check)鉴权。

```yaml
breeze:
  auth:
    route:
      exclude-path-patterns: /auth/**,/test/** #路由鉴权忽略的路径,多个逗号隔开。
      path-patterns: /** # 路由鉴权的路径，默认全部/**
      enable: true #是否开启路由鉴权，默认：开启
    annotation:
      enable: true # 是否开启注解鉴权，默默认：开启
      exclude-path-patterns: /auth/** # 注解鉴权的忽略的路径
      path-patterns: /** # 注解鉴权的路径，默认全部/**
    jwt:
      enable: true # 是否开启jwt模式，默认：不开启
      mode: stateless #jwt模式。Simple 模式：Token 风格替换，Mixin 模式：混入部分逻辑，Stateless 模式：服务器完全无状态
```

3. 文档地址

   https://sa-token.cc/doc.html

   常用方法

   ```java
   // 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
   StpUtil.login(Object id);     
   // 当前会话注销登录
   StpUtil.logout();
   
   // 获取当前会话是否已经登录，返回true=已登录，false=未登录
   StpUtil.isLogin();
   
   // 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
   StpUtil.checkLogin();
   // 获取当前会话账号id, 如果未登录，则抛出异常：`NotLoginException`
   StpUtil.getLoginId();
   
   // 类似查询API还有：
   StpUtil.getLoginIdAsString();    // 获取当前会话账号id, 并转化为`String`类型
   StpUtil.getLoginIdAsInt();       // 获取当前会话账号id, 并转化为`int`类型
   StpUtil.getLoginIdAsLong();      // 获取当前会话账号id, 并转化为`long`类型
   
   // ---------- 指定未登录情形下返回的默认值 ----------
   
   // 获取当前会话账号id, 如果未登录，则返回null 
   StpUtil.getLoginIdDefaultNull();
   
   // 获取当前会话账号id, 如果未登录，则返回默认值 （`defaultValue`可以为任意类型）
   StpUtil.getLoginId(T defaultValue);
   // 获取当前会话的token值
   StpUtil.getTokenValue();
   
   // 获取当前`StpLogic`的token名称
   StpUtil.getTokenName();
   
   // 获取指定token对应的账号id，如果未登录，则返回 null
   StpUtil.getLoginIdByToken(String tokenValue);
   
   // 获取当前会话剩余有效期（单位：s，返回-1代表永久有效）
   StpUtil.getTokenTimeout();
   
   // 获取当前会话的token信息参数
   StpUtil.getTokenInfo();
   ```

4. 其他说明

* 鉴权写入redis，在pom中添加

  ```xml
  <!--pom.xml-->
  <dependency>
     <groupId>cn.fanzy.breeze</groupId>
     <artifactId>breeze-auth-redis</artifactId>
  </dependency>
  ```

* 使用JWT，在pom中添加

  ```xml
  <dependency>
     <groupId>cn.fanzy.breeze</groupId>
     <artifactId>breeze-auth-jwt-spring-boot-starter</artifactId>
  </dependency>
  ```

  JWT风格配置。

    * simple模式：Token 风格替换,默认

    * Mixin 模式：混入部分逻辑

    * Stateless 模式：服务器完全无状态

    * statelessNotCheck：不校验过期

  在配置文件中配置即可。

  ```properties
  breeze.auth.jwt.enable=true # 是否启用jwt，默认：false
  breeze.auth.jwt.mode=stateless # 可选simple、mixin、stateless、statelessNotCheck。
  ```
