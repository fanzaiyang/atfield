package cn.fanzy.atfield.upload.service.impl;


import cn.fanzy.atfield.upload.model.FileUploadResponse;
import cn.fanzy.atfield.upload.model.MinioBucketPolicy;
import cn.fanzy.atfield.upload.property.UploadProperty;
import cn.fanzy.atfield.upload.service.UploadService;
import cn.fanzy.atfield.upload.utils.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文件上传服务实现
 *
 * @author fanzaiyang
 * @date 2023/12/18
 */
@Slf4j
public class UploadServiceImpl implements UploadService {
    private UploadProperty.MinioServerConfig config;
    private MinioClient client;

    private MinioClient innerClient;

    /**
     * 桶
     */
    private String bucket;

    /**
     * 前缀
     */
    private String prefix = "";

    private AmazonS3 amazonS3;


    @Override
    public void setConfig(UploadProperty.MinioServerConfig config) {
        this.config = config;
        this.bucket = config.getBucket();
        Assert.notNull(config, "未找到的配置！");
        client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        innerClient = MinioClient.builder()
                .endpoint(config.getInnerEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        //设置连接时的参数
        ClientConfiguration configs = new ClientConfiguration();
        //设置连接方式为HTTP，可选参数为HTTP和HTTPS
        configs.setProtocol(Protocol.HTTP);
        //设置网络访问超时时间
        configs.setConnectionTimeout(5000);
        configs.setUseExpectContinue(true);
        AWSCredentials credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
        //设置Endpoint
        AwsClientBuilder.EndpointConfiguration endPoint = new AwsClientBuilder.EndpointConfiguration(config.getEndpoint(), Regions.DEFAULT_REGION.name());
        amazonS3 = AmazonS3ClientBuilder.standard()
                .withClientConfiguration(configs)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(endPoint)
                .withPathStyleAccessEnabled(true).build();

    }

    @Override
    public MinioClient client() {
        return client;
    }

    @Override
    public MinioClient innerClient() {
        return innerClient;
    }

    @Override
    public UploadService bucket(String bucket) {
        if (StrUtil.isBlank(bucket)) {
            log.warn("参数为空，使用默认存储桶。");
            return this;
        }
        this.bucket = bucket;
        return this;
    }

    @Override
    public String getBucket() {
        return this.bucket;
    }

    @Override
    public UploadService prefix(String prefix) {
        if (StrUtil.isBlank(prefix)) {
            return this;
        }
        this.prefix = prefix.endsWith("/") ? prefix : prefix + "/";
        return this;
    }

    @Override
    public String getPrefix() {
        return StrUtil.blankToDefault(prefix, "");
    }


    @Override
    public String getBucketHost() {
        return this.config.getEndpoint();
    }

    @Override
    public void bucketExistsAndCreate(String bucket) {
        if (!StringUtils.hasLength(bucket)) {
            bucket = config.getBucket();
        }
        if (!StringUtils.hasLength(bucket)) {
            throw new RuntimeException("未配置存储桶!");
        }
        try {
            boolean exists = innerClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());
            if (!exists) {
                innerClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        try {
            String type = UploadFileTypeUtil.getFileType(file);
            String objectName = UploadObjectNameGenerate.objectName(type);
            String fileOriginalFilename = file.getOriginalFilename();
            return upload(file.getInputStream(), objectName, fileOriginalFilename, file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileUploadResponse upload(MultipartFile file, String objectName) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = UploadFileTypeUtil.getFileType(file);
                objectName = UploadObjectNameGenerate.objectName(type);
            }
            return upload(file.getInputStream(), objectName, file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileUploadResponse upload(MultipartFile file, String objectName, String contentType) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = UploadFileTypeUtil.getFileType(file);
                objectName = UploadObjectNameGenerate.objectName(type);
            }
            if (StrUtil.isBlank(contentType)) {
                contentType = file.getContentType();
            }
            return upload(file.getInputStream(), objectName, file.getOriginalFilename(), contentType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileUploadResponse upload(File file) {
        String fileType = UploadFileTypeUtil.getFileType(file);
        String objectName = UploadObjectNameGenerate.objectName(fileType);
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }


    @Override
    public FileUploadResponse upload(File file, String objectName) {
        String fileType = UploadFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = UploadObjectNameGenerate.objectName(fileType);
        }
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public FileUploadResponse upload(File file, String objectName, String contentType) {
        String fileType = UploadFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = UploadObjectNameGenerate.objectName(fileType);
        }
        if (StrUtil.isBlank(contentType)) {
            contentType = BreezeFileContentType.getContentType(fileType);
        }
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public FileUploadResponse upload(InputStream inputStream, String objectName, String fileName, String contentType) {
        Assert.notBlank(fileName, "文件名不为空！");
        Assert.notBlank(objectName, "objectName不能为空！");
        Assert.notBlank(contentType, "contentType不能为空！");
        Assert.notNull(inputStream, "文件流不能为空！");
        objectName = getPrefix() + objectName;
        if (StrUtil.endWithIgnoreCase(contentType, "jpeg")) {
            contentType = contentType.replaceAll("jpeg", "jpg");
        }
        bucketExistsAndCreate(this.bucket);

        try {
            int available = inputStream.available();
            BigDecimal decimal = new BigDecimal(available)
                    .divide(new BigDecimal(1048576), 4, RoundingMode.HALF_UP);
            ObjectWriteResponse response = innerClient.putObject(PutObjectArgs.builder()
                    .bucket(this.bucket)
                    .contentType(contentType)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            return FileUploadResponse.builder()
                    .etag(response.etag())
                    .bucket(bucket)
                    .endpoint(config.getEndpoint())
                    .objectName(objectName)
                    .fileName(fileName)
                    .fileMbSize(decimal.setScale(4, RoundingMode.HALF_UP).doubleValue())
                    .fileByteSize(available)
                    .previewUrl(getPreviewUrl(objectName))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FileUploadResponse merge(ComposeObjectArgs args) {
        try {
            bucketExistsAndCreate(this.bucket);
            ObjectWriteResponse response = innerClient.composeObject(args);
            return FileUploadResponse.builder()
                    .etag(response.etag())
                    .bucket(bucket)
                    .endpoint(config.getEndpoint())
                    .objectName(response.object())
                    .previewUrl(getPreviewUrl(response.object()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public InputStream getObject(String objectName) {
        return getObject(config.getBucket(), objectName);
    }

    @Override
    public InputStream getObject(String bucket, String objectName) {
        try {
            return innerClient.getObject(GetObjectArgs.builder()
                    .bucket(StrUtil.blankToDefault(bucket, config.getBucket()))
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObject(String objectName) {
        try {
            innerClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(config.getBucket())
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObject(String bucket, String objectName) {
        try {
            innerClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(StrUtil.blankToDefault(bucket, config.getBucket()))
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBucketPolicy() {
        return getBucketPolicy(config.getBucket());
    }

    @Override
    public String getBucketPolicy(String bucket) {
        try {
            return innerClient.getBucketPolicy(GetBucketPolicyArgs.builder()
                    .bucket(config.getBucket())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketPolicy(String objectPrefix, MinioBucketEffectEnum effect) {
        try {
            if (!objectPrefix.startsWith("/")) {
                objectPrefix = "/" + objectPrefix;
            }
            String resource = StrUtil.format("arn:aws:s3:::{}{}", this.bucket, objectPrefix);
            String bucketPolicy = getBucketPolicy(this.bucket);
            MinioBucketPolicy bean = JSONUtil.toBean(bucketPolicy, MinioBucketPolicy.class);
            List<MinioBucketPolicy.Statement> statementList = bean.getStatement();
            // 查看该文件是够已经设置为该策略
            Optional<MinioBucketPolicy.Statement> any = statementList.stream().filter(item -> item.getAction().contains(BreezeBucketPolicyEnum.GetObject.getAction()) &&
                                                                                              item.getResource().contains(resource)).findAny();
            if (any.isPresent()) {
                log.debug("该文件已设置该策略，忽略设置。");
                return;
            }
            boolean exist = false;
            for (MinioBucketPolicy.Statement item : statementList) {
                if (item.getAction().contains(BreezeBucketPolicyEnum.GetObject.getAction())) {
                    exist = true;
                    if (!item.getResource().contains(resource)) {
                        item.getResource().add(resource);
                        item.setResource(item.getResource());
                    }
                    break;
                }
            }
            if (!exist) {
                statementList.add(MinioBucketPolicy.Statement.builder()
                        .Effect(effect.name())
                        .Principal(MapUtil.of("AWS", new String[]{"*"}))
                        .Resource(CollUtil.newArrayList(resource))
                        .Action(CollUtil.newArrayList(BreezeBucketPolicyEnum.GetObject.getAction()))
                        .build());
            }
            bean.setStatement(statementList);
            setBucketPolicy(bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketPolicy(MinioBucketPolicy args) {
        try {
            String config = JSONUtil.toJsonStr(args);
            innerClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(StrUtil.blankToDefault(bucket, this.config.getBucket()))
                    .config(config)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBucketPolicy(String objectPrefix) {
        String bucketPolicy = getBucketPolicy(config.getBucket());
        if (!objectPrefix.startsWith("/")) {
            objectPrefix = "/" + objectPrefix;
        }
        String resource = StrUtil.format("arn:aws:s3:::{}{}", bucket, objectPrefix);
        MinioBucketPolicy bean = JSONUtil.toBean(bucketPolicy, MinioBucketPolicy.class);
        List<MinioBucketPolicy.Statement> statementList = bean.getStatement();
        statementList.forEach(item -> {
            if (item.getResource().contains(resource)) {
                item.getResource().remove(resource);
                item.setResource(item.getResource());
            }
        });
        List<MinioBucketPolicy.Statement> collect = statementList.stream().filter(item -> CollUtil.isNotEmpty(item.getResource()))
                .collect(Collectors.toList());
        bean.setStatement(collect);
        setBucketPolicy(bean);
    }

    @Override
    public String getPreviewUrl(String objectName) {
        try {
            if (StrUtil.isBlank(objectName)) {
                return null;
            }
            if (StrUtil.startWith(objectName, "http")) {
                return objectName;
            }
            return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(this.bucket)
                    .object(objectName)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPublicPreviewUrl(String objectName) {
        if (StrUtil.isBlank(objectName)) {
            return null;
        }
        if (StrUtil.startWith(objectName, "http")) {
            return objectName;
        }
        setBucketPolicy(objectName, MinioBucketEffectEnum.Allow);
        return config.getEndpoint().endsWith("/") ? config.getEndpoint() : (config.getEndpoint() + '/')
                                                                           + this.bucket +
                                                                           (objectName.startsWith("/") ? objectName : ("/" + objectName));
    }

    @Override
    public String initiateMultipartUpload(String objectName) {
        String contentType = MediaTypeFactory.getMediaType(objectName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        InitiateMultipartUploadResult result = amazonS3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucket, objectName)
                .withObjectMetadata(objectMetadata));
        return result.getUploadId();
    }

    @Override
    public void abortMultipartUpload(String objectName, String uploadId) {
        amazonS3.abortMultipartUpload(new AbortMultipartUploadRequest(bucket, objectName, uploadId));
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(String objectName, String uploadId, List<PartSummary> parts) {
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest()
                .withUploadId(uploadId)
                .withKey(objectName)
                .withBucketName(bucket)
                .withPartETags(parts.stream().map(partSummary -> new PartETag(partSummary.getPartNumber(), partSummary.getETag())).collect(Collectors.toList()));
        return amazonS3.completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public List<PartSummary> listParts(String objectName, String uploadId) {
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucket, objectName, uploadId);
        PartListing parts = amazonS3.listParts(listPartsRequest);
        return parts.getParts();
    }

    @Override
    public String getPresignedObjectUrl(Method method, String objectName, Integer expireDuration, TimeUnit timeUnit, Map<String, String> extraQueryParams) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, objectName)
                .withExpiration(DateUtil.offsetDay(new Date(), 1)).withMethod(HttpMethod.PUT);
        if (extraQueryParams != null) {
            extraQueryParams.forEach(request::addRequestParameter);
        }
        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString();
    }

    @Override
    public void delete(String objectName) {
        Assert.notBlank(objectName, "对象名不能为空！");
        try {
            innerClient.deleteObjectTags(DeleteObjectTagsArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (ErrorResponseException | InternalException | InvalidKeyException | InvalidResponseException |
                 InsufficientDataException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}
