package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.model.BreezeBucketPolicy;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.utils.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BreezeMinioServiceImpl implements BreezeMinioService {
    private final BreezeMinIOProperties.MinioServerConfig config;
    private final MinioClient client;

    private final MinioClient innerClient;

    public BreezeMinioServiceImpl(BreezeMinIOProperties.MinioServerConfig config) {
        this.config = config;
        Assert.notNull(config, "未找到的配置！");
        client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        innerClient = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
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
    public BreezeMinioResponse upload(String bucket, InputStream inputStream, String objectName, String fileName, String contentType) {
        Assert.notBlank(fileName, "文件名不为空！");
        Assert.notBlank(bucket, "存储桶不能为空！");
        Assert.notBlank(objectName, "objectName不能为空！");
        Assert.notBlank(contentType, "contentType不能为空！");
        Assert.notNull(inputStream, "文件流不能为空！");
        if (StrUtil.endWithIgnoreCase(contentType, "jpeg")) {
            contentType = contentType.replaceAll("jpeg", "jpg");
        }
        bucketExistsAndCreate(config.getBucket());
        try {
            int available = inputStream.available();
            BigDecimal decimal = new BigDecimal(available).divide(new BigDecimal(1048576));
            ObjectWriteResponse response = innerClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(contentType)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            return BreezeMinioResponse.builder()
                    .etag(response.etag())
                    .bucket(bucket)
                    .endpoint(config.getEndpoint())
                    .objectName(objectName)
                    .fileName(fileName)
                    .fileMbSize(decimal.setScale(2, RoundingMode.HALF_UP).doubleValue())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public BreezeMinioResponse upload(InputStream inputStream, String objectName, String fileName, String contentType) {
        return upload(config.getBucket(), inputStream, objectName, fileName, contentType);
    }

    @Override
    public BreezeMinioResponse upload(MultipartFile file) {
        try {
            String type = BreezeFileTypeUtil.getFileType(file);
            return upload(file.getInputStream(), BreezeObjectGenerate.objectName(type), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreezeMinioResponse upload(String bucket, MultipartFile file) {
        try {
            String type = BreezeFileTypeUtil.getFileType(file);
            return upload(bucket, file.getInputStream(), BreezeObjectGenerate.objectName(type), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreezeMinioResponse upload(MultipartFile file, String objectName) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = BreezeFileTypeUtil.getFileType(file);
                objectName = BreezeObjectGenerate.objectName(type);
            }
            return upload(file.getInputStream(), objectName, file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreezeMinioResponse upload(String bucket, MultipartFile file, String objectName) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = BreezeFileTypeUtil.getFileType(file);
                objectName = BreezeObjectGenerate.objectName(type);
            }
            return upload(bucket, file.getInputStream(), objectName, file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreezeMinioResponse upload(MultipartFile file, String objectName, String contentType) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = BreezeFileTypeUtil.getFileType(file);
                objectName = BreezeObjectGenerate.objectName(type);
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
    public BreezeMinioResponse upload(String bucket, MultipartFile file, String objectName, String contentType) {
        try {
            if (StrUtil.isBlank(objectName)) {
                String type = BreezeFileTypeUtil.getFileType(file);
                objectName = BreezeObjectGenerate.objectName(type);
            }
            if (StrUtil.isBlank(contentType)) {
                contentType = file.getContentType();
            }
            return upload(bucket, file.getInputStream(), objectName, file.getOriginalFilename(), contentType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreezeMinioResponse upload(File file) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        String objectName = BreezeObjectGenerate.objectName(fileType);
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public BreezeMinioResponse upload(String bucket, File file) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        String objectName = BreezeObjectGenerate.objectName(fileType);
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(bucket, FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public BreezeMinioResponse upload(File file, String objectName) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = BreezeObjectGenerate.objectName(fileType);
        }
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public BreezeMinioResponse upload(String bucket, File file, String objectName) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = BreezeObjectGenerate.objectName(fileType);
        }
        String contentType = BreezeFileContentType.getContentType(fileType);
        return upload(bucket, FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public BreezeMinioResponse upload(File file, String objectName, String contentType) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = BreezeObjectGenerate.objectName(fileType);
        }
        if (StrUtil.isBlank(contentType)) {
            contentType = BreezeFileContentType.getContentType(fileType);
        }
        return upload(FileUtil.getInputStream(file), objectName, file.getName(), contentType);
    }

    @Override
    public BreezeMinioResponse upload(String bucket, File file, String objectName, String contentType) {
        String fileType = BreezeFileTypeUtil.getFileType(file);
        if (StrUtil.isBlank(objectName)) {
            objectName = BreezeObjectGenerate.objectName(fileType);
        }
        if (StrUtil.isBlank(contentType)) {
            contentType = BreezeFileContentType.getContentType(fileType);
        }
        return upload(bucket, FileUtil.getInputStream(file), objectName, file.getName(), contentType);
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
    public void setBucketPolicy(String objectPrefix, BreezeBucketEffectEnum policy) {
        if (!objectPrefix.startsWith("/")) {
            objectPrefix = "/" + objectPrefix;
        }
        setBucketPolicy(config.getBucket(), objectPrefix, policy);
    }

    @Override
    public void setBucketPolicy(String bucket, String objectPrefix, BreezeBucketEffectEnum policy) {
        try {
            if (!objectPrefix.startsWith("/")) {
                objectPrefix = "/" + objectPrefix;
            }
            String resource = StrUtil.format("arn:aws:s3:::{}{}", bucket, objectPrefix);
            String bucketPolicy = getBucketPolicy(bucket);
            BreezeBucketPolicy bean = JSONUtil.toBean(bucketPolicy, BreezeBucketPolicy.class);
            bean.getStatement().add(BreezeBucketPolicy.Statement.builder()
                    .effect(policy.name())
                    .principal("*")
                    .resource(resource)
                    .action(CollUtil.newArrayList(BreezeBucketPolicyEnum.GetObject.getAction()))
                    .build());
            setBucketPolicy(bucket, bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketPolicy(String bucket, BreezeBucketPolicy args) {
        try {
            innerClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(StrUtil.blankToDefault(bucket, config.getBucket()))
                    .config(JSONUtil.toJsonStr(args))
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBucketPolicy(String bucket, String objectPrefix) {
        if (!objectPrefix.startsWith("/")) {
            objectPrefix = "/" + objectPrefix;
        }
        String resource = StrUtil.format("arn:aws:s3:::{}{}", bucket, objectPrefix);
        String bucketPolicy = getBucketPolicy(bucket);
        BreezeBucketPolicy bean = JSONUtil.toBean(bucketPolicy, BreezeBucketPolicy.class);
        List<BreezeBucketPolicy.Statement> statementList = bean.getStatement().stream()
                .filter(item -> !item.getResource().equals(resource))
                .collect(Collectors.toList());
        bean.setStatement(statementList);
        setBucketPolicy(bucket, bean);
    }

    @Override
    public void removeBucketPolicy(String objectPrefix) {
        String bucketPolicy = getBucketPolicy(config.getBucket());
        if (!objectPrefix.startsWith("/")) {
            objectPrefix = "/" + objectPrefix;
        }
        String resource = StrUtil.format("arn:aws:s3:::{}{}", config.getBucket(), objectPrefix);
        BreezeBucketPolicy bean = JSONUtil.toBean(bucketPolicy, BreezeBucketPolicy.class);
        List<BreezeBucketPolicy.Statement> statementList = bean.getStatement().stream()
                .filter(item -> !item.getResource().equals(resource))
                .collect(Collectors.toList());
        bean.setStatement(statementList);
        setBucketPolicy(config.getBucket(), bean);
    }
}
