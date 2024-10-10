# A.T Field 绝对领域

## 背景

在日常开发过程中，时常发现有一些简单的功能会被经常使用到，又没有一个比较好用的功能集合，因此在开发项目是需要反复配置，造成了大量不必要的重复性简单劳动，所以在从过往经验的基础上对日常使用到功能进行了一个通用封装，方便后期项目维护及开发。

# 组件使用说明

2. dependencyManagement **「推荐」**
    *
   最新版本号：![GitHub release (latest by date)](https://img.shields.io/github/v/release/fanzaiyang/breeze-spring-cloud.png)

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>cn.fanzy.atfield</groupId>
            <artifactId>atfield-bom</artifactId>
            <version>${revision}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

> Tips：使用这个方式，你不需要指定组件的版本号。锁定依赖版本。

3. 以web组件`breeze-web-spring-boot-starter`为例，在`pom.xml`配置如下：

```xml
<!-- pom.xml -->
<dependency>
    <groupId>cn.fanzy.atfield</groupId>
    <artifactId>atfield-web</artifactId>
</dependency>
```

4. 添加配置

微风Cloud组件的配置文件，都以`atfield`开头。以`atfield-web`组件为例，关闭全局异常捕获功能，只需要在配置文件中，作如下配置：

```yaml
# 关闭web全局异常捕获，默认：true
atfield:
  web:
    exception: 
      enable: true
```

组件的详细说明，在各组件介绍中，会详细说明，请阅读对应章节。文档位置：`docs`

## 贡献代码的步骤

* 在Github上fork项目到自己的repo
* 把fork过去的项目也就是你的项目clone到你的本地
* 修改代码
* commit后push到自己的库
* 登录Github在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。
* 等待维护者合并

## 许可证书

本项目的发布受[Apache 2.0 license](./LICENSE)许可认证。