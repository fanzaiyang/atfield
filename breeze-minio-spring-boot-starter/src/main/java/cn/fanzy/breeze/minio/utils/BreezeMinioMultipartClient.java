package cn.fanzy.breeze.minio.utils;

import com.google.common.collect.Multimap;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Part;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BreezeMinioMultipartClient extends MinioAsyncClient {


    public BreezeMinioMultipartClient(MinioAsyncClient client) {
        super(client);
    }

    public String getUploadId(String bucketName, String region, String objectName,Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        CreateMultipartUploadResponse response = this.createMultipartUpload(bucketName, region, objectName, headers, extraQueryParams);

        return response.result().uploadId();
    }

    public String removeMultipartUpload(String bucket, String region, String object,String uploadId, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, ServerException, InternalException, XmlParserException, InvalidResponseException, ErrorResponseException, ExecutionException, InterruptedException {
        CompletableFuture<AbortMultipartUploadResponse> response = this.abortMultipartUploadAsync(bucket, region, object, uploadId,headers, extraQueryParams);
        return response.get().uploadId();
    }
    public ObjectWriteResponse mergeMultipart(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return this.completeMultipartUpload(bucketName, region, objectName, uploadId, parts,extraHeaders, extraQueryParams);
    }

    public ListPartsResponse listMultipart(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMaker,
                                           String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return this.listParts(bucketName, region, objectName, maxParts, partNumberMaker, uploadId, extraHeaders, extraQueryParams);
    }
}
