# breeze-spring-cloud
一个简单易用的SpringCloud组件库。
* 文档地址：[ http://breeze.fanzy.cn]( http://breeze.fanzy.cn)

**微风Cloud**是基于SpringCloud开发的，一系列实用微服务业务组件库。

**微风Cloud**开发遵循以下原则：

* 开箱即用：开发者可以自由选择仅仅使用到功能，关闭不需要的功能。
* 简单配置：在开启组件功能后，无需进行复杂配置即可使用组件的基本功能。
* 个性定制：每个组件提供大量额外配置，自定义组件复杂功能。
* 高级扩展：每个组件都可以被重写，已实现个性化需求。
settings.xml
```xml
<profile>
        <id>github</id>
        <repositories>
          <repository>
            <id>central</id>
            <url>https://repo1.maven.org/maven2</url>
          </repository>
          <repository>
            <id>github</id>
            <url>https://maven.pkg.github.com/fanzaiyang/breeze-spring-cloud</url>
            <snapshots>
              <enabled>true</enabled>
            </snapshots>
          </repository>
        </repositories>
      </profile>
<server>
<id>github</id>
<username>fanzaiyang</username>
<password>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</password>
</server>
```