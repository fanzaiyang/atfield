# MinIO组件
[MinIO](https://min.io/)是一个高性能的对象存储工具。MinIO组件，是对[MinIO](https://min.io/)的进一步封装，目的是方便开发者快速集成使用MinIO服务。
## 组件说明
MinIO组件是对minio官方sdk[minio](http://docs.minio.org.cn/docs/master/java-client-api-reference)`8.2.2`的封装，使其能快速集成到spring boot中使用。该组件可以在SpringBoot中只需要简单的配置，就可以做到集成一个到多个MinIO服务。

## 快速开始

1. 引入依赖

在`pom.xml`中引入以下依赖

```xml
<dependencies>
    <dependency>
        <groupId>cn.fanzy.breeze</groupId>
        <artifactId>breeze-minio-spring-boot-starter</artifactId>
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

2. 修改配置

> ⚠️**注意：**
>
> 该组件是需要你配置minio相关服务和密钥的，否则是无法使用的。故而，此组件配置文件是必须的。

```yaml
breeze:
  minio:
    servers:
      # 第一个minio服务配置
      test:
        access-key: minioadmin #账号，必填项。
        secret-key: minioadmin #密码，必填项。
        bucket: test #默认的存储桶，必填项。
        endpoint: http://minio.zaiyang.top # minio服务地址，必填项
        inner-endpoint: http://minio.zaiyang.top # minio内网服务地址，目的加速文件上传，非必填项，默认同endpoint。
      # 第二个minio服务配置
      prod:
        access-key: minioadmin #账号，必填项。
        secret-key: minioadmin #密码，必填项。
        bucket: test2 #默认的存储桶，必填项。
        endpoint: http://minio.zaiyang.top # minio服务地址，必填项
        inner-endpoint: http://minio.zaiyang.top # minio内网服务地址，目的加速文件上传，非必填项，默认同endpoint。
```

3. 使用

```java
@Slf4j
@SpringBootTest
class BreezeMinioTests {

    /**
     * 获取存储桶策略
     */
    @Test
    void getBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String bucketPolicy = minioService.getBucketPolicy();
        System.out.println(bucketPolicy);
    }
    /**
     * 设置存储桶策略
     */
    @Test
    void setBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.setBucketPolicy("/test/2021-09-29/*", BreezeBucketEffectEnum.Allow);
    }
    /**
     * 移除存储桶策略
     */
    @Test
    void removeBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.removeBucketPolicy("/test/2021-09-29/*");
    }
    /**
     * 上传文件到默认存储桶
     */
    @Test
    void uploadFile() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        BreezeMinioResponse response = minioService.upload(FileUtil.getFile(""));
        log.info("存储结果：{}", JSONUtil.toJsonStr(response));
    }
    @Test
    void getPreviewUrl() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String previewUrl = minioService.getPreviewUrl("objectName");
        log.info("结果：{}", previewUrl);
    }
    @Test
    void getFile() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        InputStream objectName = minioService.getObject("objectName");
    }
  	/**
     * 当提供的方法不满足需要是，你可以直接获取MinIO的SDK中的MinioClient使用
     */
    @Test
    void getClient() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        MinioClient minioClient = minioService.client();
        //todo 你的自定义代码
    }
    /**
     * 指定存储桶
     */
    @Test
    void customBucket() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService = minioService.bucket("custom");
        //todo 你的自定义代码
    }
}
```

## 组件方法

> 组件提供大量的重载方法，方便开发者上传下载文件，此外MinIO的sdk也可以个性使用。
>
> 注意：你需要在你的Spring上下文中使用。

* BreezeMinioConfiguration.instance()

  获取配置文件中第一个MinIO服务。大多数情况下，一个项目中就一个MinIO服务。

* BreezeMinioConfiguration.instance(name)

  根据名称获取指定的MinIO服务，这里的name是配置文件中的节点。

* BreezeMinioService方法一览

```java
public interface BreezeMinioService {

    void setConfig(BreezeMinIOProperties.MinioServerConfig config);
    /**
     * 公网客户端
     * <pre>主要用于查询类</pre>
     *
     * @return {@link MinioClient}
     */
    MinioClient client();

    /**
     * 内网客户端
     * <pre>主要用于存储类</pre>
     *
     * @return {@link MinioClient}
     */
    MinioClient innerClient();

    /**
     * 变更存储桶
     *
     * @param bucket 桶
     */
    BreezeMinioServiceImpl bucket(String bucket);

    /**
     * 桶存在和创造
     *
     * @param bucket 桶
     */
    void bucketExistsAndCreate(String bucket);



    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file);


    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file, String objectName);


    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file, String objectName, String contentType);


    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file);

    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file, String objectName);
    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file, String objectName, String contentType);

    /**
     * 上传到默认存储桶
     *
     * @param inputStream 输入流
     * @param objectName  对象名称
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(InputStream inputStream, String objectName, String fileName, String contentType);
    /**
     * 得到对象
     *
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    InputStream getObject(String objectName);

    /**
     * 得到对象
     *
     * @param bucket     桶
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    InputStream getObject(String bucket, String objectName);

    /**
     * 删除对象
     *
     * @param objectName 对象名称
     */
    void removeObject(String objectName);

    /**
     * 删除对象
     *
     * @param bucket     桶
     * @param objectName 对象名称
     */
    void removeObject(String bucket, String objectName);

    /**
     * 桶政策
     *
     * @return {@link String}
     */
    String getBucketPolicy();

    /**
     * 桶政策
     *
     * @param bucket 桶
     * @return {@link String}
     */
    String getBucketPolicy(String bucket);

    /**
     * 制定桶政策
     *
     * @param objectPrefix 对象前缀
     * @param effect       政策
     */
    void setBucketPolicy(String objectPrefix, BreezeBucketEffectEnum effect);

    /**
     * 制定桶政策
     *
     * @param args   arg游戏
     */
    void setBucketPolicy(BreezeBucketPolicy args)    ;

    /**
     * 删除桶政策
     *
     * @param objectPrefix 对象前缀
     */
    void removeBucketPolicy(String objectPrefix);

    /**
     * 得到预览url,失效：7天
     *
     * @param objectName 对象名称
     * @return {@link String}
     */
    String getPreviewUrl(String objectName);

    /**
     * 得到公共预览url
     * <pre>
     *     ⚠️ 注意：这里会把该文件设置为公共可读，请谨慎使用。
     * </pre>
     *
     * @param objectName 对象名称
     * @return {@link String}
     */
    String getPublicPreviewUrl(String objectName);
}
```

* 关于上传的一些说明

  * 上传响应结果

  | 字段       | 类型   | 说明                                |
  | ---------- | ------ | ----------------------------------- |
  | etag       | String | 标识,MinIO SDK上传后返回此字段      |
  | endpoint   | String | MinIO地址                           |
  | bucket     | String | 文件保存的存储桶                    |
  | objectName | String | 对象名，文件在MinIO服务器的唯一名字 |
  | fileName   | String | 上传文件的原始名称                  |
  | fileMbSize | double | 文件大小，单位：Mb，保留两位小数。  |
  | previewUrl | String | 临时预览地址，7天有效               |

  * 上传的objectName

    在不指定objectName时，系统默认生成的objectName是yyyy/MM/dd/[objectId].[文件类型]

  * contentType

    强烈建议指定文件的contentType，在不指定的情况下，系统根据上传文件的扩展名，进行匹配。目前支持图片类匹配，若匹配不到，默认使用`application/octet-stream`。

  * 存储桶

    在上传或下载时，是可以指定存储桶的，而非必须使用配置文件中配置好的存储桶。配置文件的存储桶只作为默认存储桶。

    

