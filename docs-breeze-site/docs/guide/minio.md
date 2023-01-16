---
title: MinIO组件
order: 4

---

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
    </dependency>
 ... ...
</dependencies>
```

2. 修改配置

> ⚠️**注意：**
> 
> 该组件是需要你配置minio相关服务和密钥的，否则是无法使用的。故而，此组件配置文件是必须的。

```yaml
breeze:
  minio:
    api:
      init: /breeze/minio/multipart/init # 默认此地址
      merge: /breeze/minio/multipart/merge # 默认此地址
      table-name: sys_multipart_file_info #默认此名称
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
  
  | 字段         | 类型     | 说明                   |
  | ---------- | ------ | -------------------- |
  | etag       | String | 标识,MinIO SDK上传后返回此字段 |
  | endpoint   | String | MinIO地址              |
  | bucket     | String | 文件保存的存储桶             |
  | objectName | String | 对象名，文件在MinIO服务器的唯一名字 |
  | fileName   | String | 上传文件的原始名称            |
  | fileMbSize | double | 文件大小，单位：Mb，保留两位小数。   |
  | previewUrl | String | 临时预览地址，7天有效          |
  
  * 上传的objectName
    
    在不指定objectName时，系统默认生成的objectName是yyyy/MM/dd/[objectId].[文件类型]
  
  * contentType
    
    强烈建议指定文件的contentType，在不指定的情况下，系统根据上传文件的扩展名，进行匹配。目前支持图片类匹配，若匹配不到，默认使用`application/octet-stream`。
  
  * 存储桶
    
    在上传或下载时，是可以指定存储桶的，而非必须使用配置文件中配置好的存储桶。配置文件的存储桶只作为默认存储桶。

## 分片上传

> 这是基于MinIO的分片上传，上传和业务逻辑分离，提高上传效率。支持秒传、断点续传。

### 上传原理

![上传原理](https://breeze.fanzy.cn/uploader-flow.png)

对于前端开发者而言，需要做一下工作。

> **注意：**
> 
> 后台返回的数据中，包含各个分片上传状态和具体的每一片上传地址，前端需要针对不同分片上传不同地址。

* 计算文件md5，可以使用npm包`spark-md5`，对于ts用户可能还需要安装`@types/spark-md5`

* 计算好md5后需要把md5传给后台，校验文件上传进度。

* 根据前端返回数据，进行下一步工作。
  
  * 秒传：当后台返回完成时，直接完成即可。
  
  * 断点续传：后台返回的分片数据中有部分标识为完成的前端不用再对此分片进行操作，直接完成即可。

### 开始使用

#### 创建数据库表

> 断点续传和秒传需要数据库支持。

这里的数据库表名可以自定义，需要在配置文件中声明表名`breeze.minio.api.table-name`，组件默认表名是：`sys_multipart_file_info`

```sql
CREATE TABLE `sys_multipart_file_info` (
  `id` varchar(36) NOT NULL,
  `identifier` varchar(64) DEFAULT NULL COMMENT '文件的唯一标识identifier（md5摘要）',
  `upload_id` varchar(128) DEFAULT NULL COMMENT 'MinIO上传ID',
  `file_name` varchar(512) DEFAULT NULL,
  `bucket_host` varchar(255) DEFAULT NULL COMMENT '存储桶host地址',
  `bucket_name` varchar(200) DEFAULT NULL COMMENT '存储桶名称',
  `object_name` varchar(1024) DEFAULT NULL,
  `total_chunk_num` int(11) DEFAULT NULL COMMENT '分片个数',
  `total_file_size` bigint(20) DEFAULT NULL,
  `chunk_size` bigint(20) DEFAULT NULL COMMENT '每片按照多大分割',
  `begin_time` datetime DEFAULT NULL COMMENT '上传开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '上传结束时间',
  `spend_second` int(11) DEFAULT NULL COMMENT '后台上传耗时',
  `status` smallint(1) DEFAULT NULL COMMENT '文件状态：0-上传中，1-上传完成',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标志,0:未删除 1:已删除',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `revision` int(11) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
```

#### 后端使用

1. 方式一，自定义请求接口`controller`。

只需要在需要位置注入`BreezeMultipartFileService`即可，示例如下：

```java
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final BreezeMultipartFileService breezeMultipartFileService;
    @PostMapping("/before")
    public JsonContent<BreezePutMultipartFileResponse> before(@RequestBody BreezePutMultipartFileArgs args){
        BreezePutMultipartFileResponse upload = breezeMultipartFileService.beforeUpload(args);
        return JsonContent.success(upload);
    }
    
    @GetMapping("/merge")
    public JsonContent<BreezeMinioResponse> merge(String identifier){
        BreezeMinioResponse response = breezeMultipartFileService.mergeChunk(identifier,null);
        return JsonContent.success(response);
    }
}
```

2. 方式二，使用组件的controller。**「推荐」**
   
   该方式可以通过配置文件修改接口地址，默认组件上传的两个接口地址分别是：
   
   * 初始化：/breeze/minio/multipart/init
   
   * 合并：/breeze/minio/multipart/merge

3. 当与admin组件配合使用时建议使用admin组件附件模块**「推荐」**
