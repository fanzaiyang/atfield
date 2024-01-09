package cn.fanzy.atfield.file.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.minio.*;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class BreezeMinioMultipartClient extends MinioAsyncClient {


    public BreezeMinioMultipartClient(MinioAsyncClient client) {
        super(client);
    }

    public String getUploadId(String bucketName, String region, String objectName) {
        try {
            HashMultimap<String, String> headers = HashMultimap.create();
            headers.put("Content-Type", "application/octet-stream");
            CompletableFuture<CreateMultipartUploadResponse> future = super.createMultipartUploadAsync(bucketName, region, objectName, headers, null);
            log.info("生成uploadId:{},objectName:{},bucketName:{}", future.get().result().uploadId(),future.get().result().objectName(),future.get().result().bucketName());
            return future.get().result().uploadId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String removeMultipartUpload(String bucket, String region, String object, String uploadId, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) {
        try {
            CompletableFuture<AbortMultipartUploadResponse> response = this.abortMultipartUploadAsync(bucket, region, object, uploadId, headers, extraQueryParams);
            return response.get().uploadId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectWriteResponse mergeMultipart(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) {
        try {
            CompletableFuture<ObjectWriteResponse> future = super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ListPartsResponse listMultipart(String bucketName, String region, String objectName, String uploadId) {
        try {
            CompletableFuture<ListPartsResponse> future = super.listPartsAsync(bucketName, region, objectName, null, null, uploadId, null, null);
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
