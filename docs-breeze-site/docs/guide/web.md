---
title: WEB组件
order: 1
toc: content
---

WEB组件`breeze-web-spring-boot-starter`包含web项目常用配置。

## 🧾功能清单

| 功能名称     | 功能说明                                            | 备注  |
| -------- | ----------------------------------------------- | --- |
| 缓存管理     | 默认实现了内存，Redis缓存。开发者可快速使用缓存。                     | 开启  |
| 全局异常     | 重写了SpringBoot默认异常处理机制，拦截日常中常用的异常。封装成结构化数据，返回前端。 | 开启  |
| 全局响应     | 封装全局响应类。规范所有响应结果。开发者可通过配置设置成功失败code。            | 开启  |
| 全局过滤器    | 拦截过滤器中的异常，并返回给前端结构化数据。复制body请求参数，防止只取一次缺陷。      | 开启  |
| Redis序列化 | 解决使用RedisTemplate保存的内容乱码问题。                     | 按需  |
| 分布式锁     | 通过一个注解实现分布式锁。                                   | 按需  |
| 接口限流     | 通过一个注解实现接口并发限制。可精确到IP。如：某接口1秒内允许访问100次。         | 按需  |
| IP校验     | 支持全局和注解两种方式，对IP黑白名单进行访问限制🚫。                    | 开启  |
| 字段脱敏     | 注解方式实现人名、身份证、手机号、密码、银行卡号脱敏。                     | 开启  |
| 验证码      | 支持图片、短信和邮件3种验证码方式。                              | 按需  |
| 🔐接口安全   | 注解方式，用于方法请求n次后触发安全验证。示例：登录失败3次后，需要图片验证码验证。      | 开启  |
| Swagger  | OpenAPI3规范文档                                    | 开启  |
| 🔧工具类    | 封装了若干常用工具类，如：获取请求参数，获取客户端IP等                    | 开启  |

## 如何使用

1. 引入依赖

在`pom.xml`引入依赖文件

```xml
<dependencies>
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-web-spring-boot-starter</artifactId>
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

2. 修改配置文件

> 如无特殊要求，无需进行任何配置即可使用。

## 缓存管理

组件默认实现了基于内存的缓存管理，当引入`spring-boot-starter-data-redis`依赖时，开启redis缓存模式。

### 开始使用

在你要使用缓存的地方引入`BreezeCacheService`类即可，该类提供保存、获取和删除基本功能。

> 默认情况下，系统会优先使用redis进行缓存，如没有redis则使用内存。

示例：

```java
@SpringBootTest
class BreezeWebCacheTests {

    @Autowired
    private BreezeCacheService breezeCacheService;

    /**
     * 保存缓存内容
     */
    @Test
    void save() {
        // 过期秒数
        int expireSecond=300;
        breezeCacheService.save("key","value",300);
    }

    /**
     * 根据key获取缓存内容
     */
    @Test
    void get() {
        Object key = breezeCacheService.get("key");
        System.out.println(key);
    }

    /**
     * 根据Key删除缓存
     */
    @Test
    void delete() {
        breezeCacheService.remove("key");
    }
}
```

### 缓存方式

缓存方式分为

* auto —— 自动选择redis或memory，优先redis（默认）
* memory —— 内存。
* redis —— redis,需要引入redis依赖

大多数情况不需要你手动指定缓存方式，如需特别处理，请修改配置文件

```yaml
breeze:
  web:
    cache:
      type: auto # 可选值auto、memory、redis
```

## 全局异常

全局异常默认情况捕获Spring上下文中的所有异常和过滤器中发生的异常，以及定制了默认的错误页面。

> 默认情况下开启了全局异常拦截。
> 
> ⚠️注意
> 
> 所有的异常返回到前端的HTTP状态码，均为：200，不需要前端处理catch响应内容。

1. 配置文件说明

```yaml
breeze:
  web:
    exception:
      enable: true #是否开启全局异常，默认：true
      replace-basic-error: true # 是否替换springboot默认basicError，默认：true，推荐：true
```

### 默认错误页面

组件重写了springboot的basicError。如果关闭的话，修改配置即可：`breeze.web.exception.replace-basic-error=false`

效果如下：

![basicerror](https://raw.githubusercontent.com/fanzaiyang/breeze-spring-cloud/master/docs-breeze-site/public/basicerror.png)

拦截到异常，返回前台的JSON数据格式如下：

JSON返回类见：`cn.fanzy.breeze.web.model.JsonContent`关于此类的详细说明，见下文。

```json
{
    "id": "11322416650626944",
    "code": 404,
    "message": "Not Found",
    "data": "/auth/test",
    "now": "2022-09-21 14:39:27",
    "success": false,
    "exData": null
}
```

### 异常拦截的类

> 一般情况下，返回code是40x的是由客户端原因造成的问题。

* HttpMessageNotReadableException 400
* IllegalArgumentException 400 参数解析失败
* HttpRequestMethodNotSupportedException 405 请求方式错误，查看POST、GET...
* HttpMediaTypeNotSupportedException 415 
* NullPointerException 500
* ServletException 500
* IOException 500
* MissingServletRequestParameterException 400 请求参数有误相关
* MethodArgumentTypeMismatchException 400
* ValidationException 400
* ConstraintViolationException 400
* IndexOutOfBoundsException 500
* CustomException 500
* IllegalStateException 400
* SQLSyntaxErrorException 500
* SQLException 500
* NoHandlerFoundException 404
* MethodArgumentNotValidException 400
* MaxUploadSizeExceededException 400
* RuntimeException 500
* Exception 500

### 扩展自定义异常拦截

~~只需要在你写的异常拦截类上加上`@AutoConfigureBefore(BreezeWebExceptionConfiguration.class)`或者`@Order(Ordered.HIGHEST_PRECEDENCE)`顺序要在`BreezeWebExceptionConfiguration.class`之前。~~

示例：

```java
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(BreezeWebExceptionConfiguration.class)
@RestControllerAdvice
public class BreezeAuthExceptionConfiguration {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public JsonContent<String> handleRuntimeException(HttpServletRequest request, NotLoginException e) {
        String type = e.getType();
        String message = "";
        if (NotLoginException.NOT_TOKEN.equals(type)) {
            message = "未提供token，请重新登录！";
        } else if (NotLoginException.INVALID_TOKEN.equals(type)) {
            message = "令牌无效，请重新登录！";
        } else if (NotLoginException.TOKEN_TIMEOUT.equals(type)) {
            message = "登录已过期，请重新登录！";
        } else if (NotLoginException.BE_REPLACED.equals(type)) {
            message = "当前账号已被其他客户端登录！";
        } else if (NotLoginException.KICK_OUT.equals(type)) {
            message = "您已被强制下线！";
        } else {
            message = e.getMessage();
        }
        log.error(StrUtil.format("「微风组件」请求失败,拦截到NotLoginException异常：{}", message), e);
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Auth鉴权异常拦截> 相关的配置");
    }

}
```

## JSON响应

> 该模块定义了返回前端实体类，实体类属性说明：

| 属性      | 类型      | 说明                               |
| ------- | ------- | -------------------------------- |
| id      | String  | 请求链路唯一ID                         |
| code    | Int     | 返回错误码，默认：200标识成功。                |
| message | String  | 返回的消息                            |
| data    | T       | 泛型对象，Object。                     |
| success | boolean | 是否成功，默认code=200时，此为true，其它为false |
| now     | String  | 当前时间，格式：yyyy-MM-dd HH:mm:ss      |
| exData  | object  | 额外返回内容，根据实际业务情况使用即可。             |

配置参数：

```yaml
breeze:
    model:
      success-code: 200 # 成功code，默认200
      success-message: 操作成功！ #成功的响应消息，默认：操作成功！
      error-code: -100 # 失败code，默认-100
      error-message: 操作失败！ #失败的响应消息，默认：操作失败！
```

## 过滤器

组件配置了默认过滤器，有两个作用：1. 拦截过滤器中的异常，并返回给前端。2. 复制body的请求参数，防止在拦截器中拿到后，造成的丢失问题。

> 目前该类不允许被重写或关闭

## Redis序列化

目的解决使用`RedisTemplate`保存的内容乱码问题，及创建`RedisTemplate<String, Object>`Bean，用于上下文使用。

当引入`spring-boot-starter-data-redis`依赖后生效，且不可更改。

### 分布式锁

> 分布式锁是居于`redisson`实现的，除了框架提供方法外你可以直接使用`redisson`特性。

> 使用前需要你引入`redisson-spring-boot-starter`或`breeze-auth-redis`中的一个即可。

* 加入依赖「**推荐**」
  
  ```xml
  <dependency>
      <groupId>cn.fanzy.breeze</groupId>
      <artifactId>breeze-auth-redis</artifactId>
  </dependency>
  ```

* 方法处加注解`@LockDistributed("KEY_NAME")`
  
  ```java
  @LockDistributed("test_unlock")
  @GetMapping("/unlock")
  public JsonContent<Object> unlock() {
      log.info("执行方法。。。");
      ThreadUtil.sleep(3000);
      return JsonContent.success();
  }
  ```

### 分布式限流

限流是居于`redisson`的`RRateLimiter`实现的，除了框架提供方法外你可以直接使用`redisson`特性。

默认限流：1秒1000并发。可通过注解修改。

* 加入依赖
  
  ```xml
  <dependency>
      <groupId>cn.fanzy.breeze</groupId>
      <artifactId>breeze-auth-redis</artifactId>
  </dependency>
  ```

* 方法处加注解`@RateLimit`即可。
  
  ```java
  /**
   * 表示该接口：1秒内，并发请求10次。
   */
  @RateLimit(rateInterval = 100,rate = 1)
  @GetMapping("/rates")
  public JsonContent<Object> rate() {
      log.info("执行方法。。。rate");
      return JsonContent.success();
  }
  ```

* IP进行限流
  
  @RateLimit(rateInterval = 100,rate = 1,**useIp = true**)

## IP检查

该模块是用于配置IP白名单或黑名单来限定特定的IP允许访问此接口。默认该功能是关闭的，需要手动启用。

该功能分为：全局IP检查和注解IP检查

### 配置文件

```yaml
breeze:
    ip:
      enable: true #是否启用IP检查，默认：false
      allowed: 172.0.0.1,192.168.0.1 # 允许访问的IP集合，多个逗号个隔开
      deny: 192.168.13.1,192.168.13.2 # 不允许访问的IP集合，多个逗号个隔开
      path-patterns: /auth/**,/user/** # 根据请求路径拦截，默认：null不生效
```

### 如何使用

#### 1. 注解方式

在你写的方法上添加`@BreezeIpCheck`即可。该注解允许指定IP白名单、黑名单，若不指定则使用配置文件中配置的IP。

示例代码：

```java
// 使用配置文件中配置的IP
@BreezeIpCheck
// 使用注解中配置的IP
@BreezeIpCheck(value = {"127.0.0.1","127.0.0.2"},deny = {"192.168.13.1"})
@GetMapping("/user")
public JsonContent<Object> getUser() {
    return JsonContent.success("登录认证：只有登录之后才能进入该方法");
}
```

#### 2. 全局拦截

根据配置的路径拦截IP，在配置文件的`path-patterns`补全需要拦截的路径即可。

##### 自定义检查

1. 自定义全局异常检查

实现接口`BreezeIpGlobalCheckService`并交由Spring来管理。示例：

```java
@Slf4j
@Component
@AllArgsConstructor
public class BreezeIpDefaultGlobalCheckService implements BreezeIpGlobalCheckService {
    private final BreezeIpProperties properties;

    @Override
    public void handler(ServletWebRequest servletWebRequest) {
        String clientIp = SpringUtils.getClientIp(servletWebRequest.getRequest());
        // todo 读取数据库执行校验
    }
}
```

2. 自定义注解的检查
   
   在注解`@BreezeIpCheck(handler=CustomIpHandler.class)`添加你的实现类。该类必须实现接口`BreezeIpCheckService`，示例：
   
   * 添加注解到方法
   
   ```java
   @BreezeIpCheck(handler = CustomIpCheckHandler.class)
   @GetMapping("/user/2")
    public JsonContent<Object> getUser2(){
      return JsonContent.success("");
    }
   ```
   
   * 实现接口
   
   ```java
   public class CustomIpCheckHandler implements BreezeIpCheckService {
       @Override
       public void handler() {
           JdbcTemplate jdbcTemplate = SpringUtils.getBean(JdbcTemplate.class);
           // todo 查询数据库校验
       }
   }
   ```

## 字段脱敏

> 很多时候我们希望返回给前端的数据中某个字段需要进行脱敏处理，这时你可以使用该功能实现你的需求。
> 
> 该功能支持：身份证号、密码、手机号、真实姓名、银行卡脱敏。

### 使用方法

在需要脱敏的实体类的属性上添加注解`BreezeSensitive`即可。示例：

```java
@Data
public class SysUser {
    private String id;
    /**
     * 姓名第一位脱敏(不考虑复姓，特殊姓氏)
     * 如：李**
     */
    @BreezeSensitive(BreezeSensitiveEnum.NAME)
    private String name;
    /**
     * 银行卡脱敏，保留前4位和后4位，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.BANK_NUMBER)
    private String bankNum;
    /**
     * 身份证脱敏，保留前3位和后4位，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.ID_CARD)
    private String idnum;
    /**
     * 手机号码脱敏，前三后四脱敏，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.MOBILE_PHONE)
    private String phone;

    /**
     * 密码脱敏：***代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.PASSWORD)
    private String password;
}
```

## 工具类

封装了一些常用工具类，更多工具类推荐使用hutool.

### HttpUtil

* redirect(String url) 
  * 携带指定的信息重定向到指定的地址
* out(HttpServletResponse response, Object data) 
  * 将指定的信息按照json格式输出到指定的响应
* stack(HttpServletRequest request)
  * 打印请求中携带的查询参数和请求头信息
* getRequestId
  * 获取链路的TraceId，引入了三方[Tog](https://tlog.yomahub.com/)组件。
* download(File file, HttpServletResponse response)有多个重载方法
  * 将文件放到响应流，用于前端下载。
* extract(ServletWebRequest request, String key)
  * 请请求中获取指定的参数。

### SpringUtils

spring上下文工具类，帮助你快速获取Bean、HttpServletResponse、HttpServletRequest等，推荐使用hutool提供的`SpringUtil`

SpringUtils继承自hutool提供的`SpringUtil`。出了hutool自带方法外，此类支持：

* getClientIp()
  * 获取客户端IP地址。
* isJson(HttpServletRequest request)
  * 判断请求是否时json方式
* getCurrentProcessId
  * 获取系统进程PID
* getRequestParams
  * 获取前端的请求参数query+body
* getRequestMethod
  * 获取当前请求方式：GET、POST、...

## 验证码

验证码功能默认是关闭的，需要你在配置文件中启用。验证码支持图片验证码、短信验证码、邮箱验证码3种。

配置说明：

```yaml
breeze:
  web:
    code:
      enable: true
      delete-on-success: true #是否在验证成功后删除验证过的验证码,默认为true
      show-log: false #是否显示加载日志，默认为false
      prefix: validate_code_ #将验证码存储到Redis或内存时的key的前缀，默认值为validate_code_
      retry-count: 1 #重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。全局配置，可被子配置覆盖。
      image: #图形验证码#
        code-key: clientId #从请求中获取图形验证码的客户端的参数，默认值为 clientId
        code-value: code #请求中获取图形验证码对应的值的参数，默认值为 code
        length: 4 #验证码的长度,默认为4
        expire-in: 300 #验证码的失效时间，单位秒，默认为300s
        contain-letter: false #验证码是否包含字母,默认不包含
        contain-number: true #验证码是否包含数字,默认包含
        retry-count: 1 #重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
        width: 70 #验证码的宽度,默认为70
        height: 28 #验证码的高度,默认为 28
        fringe: false #是否生成干扰条纹背景，默认为false
      sms: #短信验证码#
        code-key: mobile #从请求中获取短信验证码的发送目标(手机号)的参数，默认值为 mobile
        code-value: code #请求中获取短信验证码对应的短信内容的参数，默认值为 phone_code
        retry-count: 1 #重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
        length: 4 #验证码的长度,默认为4
        expire-in: 300 #验证码的失效时间，单位秒，默认为300s
        contain-letter: false #验证码是否包含字母,默认不包含
        contain-number: true #验证码是否包含数字,默认包含
      email: #邮箱验证码#
        code-key: email #从请求中获取短信验证码的发送目标(手机号)的参数，默认值为 phone
        code-value: code #请求中获取短信验证码对应的短信内容的参数，默认值为 phone_code
        retry-count: 1 #重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
        length: 4 #验证码的长度,默认为4
        expire-in: 300 #验证码的失效时间，单位秒，默认为300s
        contain-letter: false #验证码是否包含字母,默认不包含
        contain-number: true #验证码是否包含数字,默认包含
        title: 帐号保护验证 #验证码邮箱的标题
        content-template: 您的验证码的内容为{0} ,验证码的有效时间为 {1} 秒 #验证码邮箱的内容模板
```

将验证码工具注入到需要使用到验证码的地方，注入代码如下：

```java
@Autowired
private BreezeCodeProcessor codeProcessor;
```

> ⚠️注意：
> 
> 在生成验证码和验证验证码的请求中，请求参数中的clientId，mobile,email参数由code-key属性,image_code参数由code-value属性决定。在某些极端情况下，可以通过这两个配置修改请求参数。

### 图形验证码

### 验证码生成

在注入一个验证码工具后，通过以下代码即可快速生成一个图形验证码。 具体的示例代码如下：

```java
/** 自动生成图形验证码并返回前端图片流 */
@GetMapping("/image/create")
public void createImageCode(HttpServletRequest request, HttpServletResponse response,String clientId) {
    BreezeCode code = processor.createAndSend(new ServletWebRequest(request,response), clientId,BreezeCodeType.IMAGE);
    log.info("生成的code：{}",code.getCode());
}
/** 自动生成图形验证码后自主返回图片base64 */
@GetMapping("/image/get")
public JsonContent<String> imageCode(HttpServletRequest request,String clientId) {
    BreezeImageCode code = (BreezeImageCode) processor.create(new ServletWebRequest(request),clientId, BreezeCodeType.IMAGE);
    log.info(code.getCode());
    return JsonContent.success(code.getImageBase64());
}
```

### 验证码校验

#### 注解

在需要校验验证码的方法上添加注解`@BreezeCodeChecker`，示例：

> 注意：
> 
> 你需要在此请求中携带验证码的key和value值。

```java
// 注解需要指定验证码类型，BreezeCodeType.IMAGE表示为图形验证码。
@BreezeCodeChecker(BreezeCodeType.IMAGE)
@GetMapping("/image/valid")
public JsonContent<String> imageValid(HttpServletRequest request,String clientId,String code) {
    return JsonContent.success();
}
```

#### 代码校验

在你需要校验的地方执行

```java
processor.validate(new ServletWebRequest(request,null),BreezeCodeType.IMAGE);
```

#### 自定义实现

针对于图形验证码，组件对图形验证码的生成做一个默认实现，如果生成的图形验证码的内容不满足用户需要，用户可以自定义一个名为 `breezeImageCodeGenerator` 的实例注入到spring中即可。

```java
@Slf4j
@Component
@AllArgsConstructor
public class BreezeImageCodeGenerator implements BreezeCodeGenerator<BreezeImageCode> {
    @Override
    public BreezeImageCode generate(ServletWebRequest servletWebRequest, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(image.getWidth(), image.getHeight(), image.getLength(), 2);
        String code = RandomStringUtils.random(image.getLength(), image.getContainLetter(), image.getContainNumber());
        Image captchaImage = lineCaptcha.createImage(code);
        BreezeImageCode imageCode = new BreezeImageCode(code, image.getRetryCount() == null ? properties.getRetryCount() : image.getRetryCount(),image.getExpireIn());
        imageCode.setImage(ImgUtil.copyImage(captchaImage,BufferedImage.TYPE_INT_RGB));
        imageCode.setImageBase64(ImgUtil.toBase64DataUri(captchaImage,ImgUtil.IMAGE_TYPE_PNG));
        imageCode.setCode(code);
        return imageCode;
    }

    @Override
    public String generateKey(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        return HttpUtil.extract(request,image.getCodeKey())+"";
    }

    @Override
    public String getCodeInRequest(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        return HttpUtil.extract(request,image.getCodeValue())+"";
    }

}
```

### 邮件验证码

在使用邮箱验证码时，需要先进行下述配置：

在项目中导入邮件发送相关的依赖

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

在项目的配置文件中加入邮件发送相关的配置属性

```yaml
spring.mail.default-encoding=UTF-8
spring.mail.host=邮箱服务器
spring.mail.username=完整的邮箱地址
spring.mail.password=密码
spring.mail.port=465
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000
spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
spring.mail.properties.mail.smtp.socketFactory.fallback=false
spring.mail.properties.mail.smtp.socketFactory.port=465
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

> 注意：
> 
> 如果不加入以上配置，在使用邮件验证码时会提示 【验证码处理器不存在】，且不能正确发送邮件验证码。

邮件验证码的发送代码与图形验证码的发送方法基本一致，只需要将BreezeCodeType.IMAGE改成BreezeCodeType.EMAIL即可。

### 短信验证码

由于短信验证码的特殊性，不同的短信提供商有不同的发送接口，因此在使用短信验证码功能之前，需要完成自己的名为 BreezeSmsSendHandler短信发送器,并将其注入到spring上下文之中。

```java
@Slf4j
@Component
public class BreezeDefaultSmsSendHandler implements BreezeSmsSendHandler {
    @Override
    public void send(String mobile, String code, ServletWebRequest request) {
         log.debug("【短信验证码发送器】向手机号 {} 发送短信验证码，验证码的内容为 {} ", target, code);
        // todo 执行发送逻辑
    }
}
```

短信验证码的发送代码与图形验证码的发送方法基本一致，只需要将BreezeCodeType.IMAGE改成BreezeCodeType.SMS即可。

## 安全验证

该组件用于登录授权时错误次数校验，当错误发生x次后就行账号锁定。该组件集成了验证码验证模块。

### 配置文件说明

```yaml
breeze:
  web:
    safe:
      login-failed-max-num: 5 #允许最大登录失败次数。默认5从
      need-code: true #是否需要验证码，默认：false
      login-failed-show-code-max-num: 3 #错误次数达到3次后，启用验证码
      login-key: username #验证的登录唯一key
      single-ip: true #按照客户端IP进行限制，默认：true，即只限制当前IP，换个IP仍可以登录。
```

### 使用

在接口上加上注解`@BreezeSafe`即可。

注解说明：

* **value** 执行验证码类型CodeType,默认图形验证码
* **loginKey**  登录的对象名，优先取此，若为空，取配置文件的`breeze.web.safe.login-key`。

## Swagger

> 该模块基于的是[Knife4j](https://doc.xiaominfo.com/docs/quick-start)开发，使用OpenAPI3规范。

### 开始使用

默认情况已经启用了swagger，你不需要做额外引入，只需要在配置文件中配置扫描的包即可。

### 配置文件方式

这里使用[springdoc](https://springdoc.org/)配置文件。

```yaml
springdoc:
  group-configs:
    - group: '默认'
      packages-to-scan: # 你的包名，比如: cn.fanzy.breeze.demo
```

### bean方式

```java
@Slf4j
@AllArgsConstructor
@Configuration
public class BreezeAdminSwaggerConfig {
    @Bean
    public GroupedOpenApi breezeAdminApi(){
        return GroupedOpenApi.builder()
                .group("微风组件")
                .pathsToMatch("/**")
                .packagesToScan("cn.fanzy.breeze.admin")
                .build();
    }
}
```

### OpenAPI3规范

| swagger2           | OpenAPI 3                                                       | 注解位置                         |
| ------------------ | --------------------------------------------------------------- | ---------------------------- |
| @Api               | @Tag(name = “接口类描述”)                                            | Controller 类上                |
| @ApiOperation      | @Operation(summary =“接口方法描述”)                                   | Controller 方法上               |
| @ApiImplicitParams | @Parameters                                                     | Controller 方法上               |
| @ApiImplicitParam  | @Parameter(description=“参数描述”)                                  | Controller 方法上 @Parameters 里 |
| @ApiParam          | @Parameter(description=“参数描述”)                                  | Controller 方法的参数上            |
| @ApiIgnore         | @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden | -                            |
| @ApiModel          | @Schema                                                         | DTO类上                        |
| @ApiModelProperty  | @Schema                                                         | DTO类上                        |
