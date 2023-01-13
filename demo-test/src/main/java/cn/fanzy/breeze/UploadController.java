package cn.fanzy.breeze;

import cn.fanzy.breeze.minio.model.BreezeMergeMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/merge")
    public JsonContent<BreezeMinioResponse> merge(@RequestBody BreezeMergeMultipartFileArgs args){
        BreezeMinioResponse response = breezeMultipartFileService.mergeChunk(args);
        return JsonContent.success(response);
    }
}
