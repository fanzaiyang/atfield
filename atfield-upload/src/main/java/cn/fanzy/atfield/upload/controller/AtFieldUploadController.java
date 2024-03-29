package cn.fanzy.atfield.upload.controller;

import cn.fanzy.atfield.upload.model.ParamFilePartPutDto;
import cn.fanzy.atfield.upload.utils.Resp;
import cn.fanzy.atfield.upload.service.AtFieldPartUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
@ConditionalOnProperty(prefix = "breeze.minio.api", name = {"enable"}, havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(JdbcTemplate.class)
public class AtFieldUploadController {

    private final AtFieldPartUploadService uploadService;

    @PostMapping("${breeze.minio.api.init:/breeze/minio/multipart/init}")
    public Object init(@RequestBody ParamFilePartPutDto args) {
        return Resp.convert(uploadService.beforeUpload(args));
    }

    @GetMapping("${breeze.minio.api.presigned:/breeze/minio/multipart/presigned}")
    public Object getPreSignedObjectUrl(String identifier, Integer partNumber, String minioConfigName) {
        return Resp.convert(uploadService.getPreSignedObjectUrl(identifier, partNumber, minioConfigName));
    }

    @GetMapping("${breeze.minio.api.merge:/breeze/minio/multipart/merge}")
    public Object merge(String identifier) {
        return Resp.convert(uploadService.mergeChunk(identifier, null));
    }
}
