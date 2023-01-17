package cn.fanzy.breeze.minio.controller;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezePutMultiPartFile;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
@ConditionalOnProperty(prefix = "breeze.minio.api", name = {"enable"}, havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(JdbcTemplate.class)
public class BreezeMinioController {

    private final BreezeMultipartFileService breezeMultipartFileService;

    @PostMapping("${breeze.minio.api.init:/breeze/minio/multipart/init}")
    public JsonContent<BreezePutMultipartFileResponse> init(@Valid @RequestBody BreezePutMultipartFileArgs args) {
        BreezePutMultipartFileResponse response = breezeMultipartFileService.beforeUpload(args);
        return JsonContent.success(response);
    }

    @GetMapping("${breeze.minio.api.presigned:/breeze/minio/multipart/presigned}")
    public JsonContent<BreezePutMultiPartFile> getPresignedObjectUrl(String identifier, Integer partNumber, String minioConfigName) {
        BreezePutMultiPartFile partFile = breezeMultipartFileService.getPresignedObjectUrl(identifier, partNumber, minioConfigName);
        return JsonContent.success(partFile);
    }

    @GetMapping("${breeze.minio.api.merge:/breeze/minio/multipart/merge}")
    public JsonContent<BreezeMinioResponse> merge(String identifier) {
        BreezeMinioResponse response = breezeMultipartFileService.mergeChunk(identifier, null);
        return JsonContent.success(response);
    }
}
