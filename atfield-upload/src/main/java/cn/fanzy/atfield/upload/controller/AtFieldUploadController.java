package cn.fanzy.atfield.upload.controller;

import cn.fanzy.atfield.upload.model.ParamFilePartPutDto;
import cn.fanzy.atfield.upload.service.UploadPartService;
import cn.fanzy.atfield.upload.utils.Resp;
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
@ConditionalOnProperty(prefix = "atfield.upload.api", name = {"enable"}, havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(JdbcTemplate.class)
public class AtFieldUploadController {

    private final UploadPartService uploadService;

    @PostMapping("${atfield.upload.api.init:/upload/multipart/init}")
    public Object init(@RequestBody ParamFilePartPutDto args) {
        return Resp.convert(uploadService.beforeUpload(args));
    }

    @GetMapping("${atfield.upload.api.presigned:/upload/multipart/presigned}")
    public Object getPreSignedObjectUrl(String identifier, Integer partNumber, String minioConfigName) {
        return Resp.convert(uploadService.getPreSignedObjectUrl(identifier, partNumber, minioConfigName));
    }

    @GetMapping("${atfield.upload.api.merge:/upload/multipart/merge}")
    public Object merge(String identifier) {
        return Resp.convert(uploadService.mergeChunk(identifier, null));
    }
}
