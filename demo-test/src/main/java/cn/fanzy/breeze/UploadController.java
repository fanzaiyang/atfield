package cn.fanzy.breeze;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list")
    public JsonContent<Object> list(String uploadId,String minioConfigName){
        return JsonContent.success(breezeMultipartFileService.queryListPart(uploadId,minioConfigName));
    }
    @GetMapping("/merge")
    public JsonContent<BreezeMinioResponse> merge(String identifier){
        BreezeMinioResponse response = breezeMultipartFileService.mergeChunk(identifier,null);
        return JsonContent.success(response);
    }
}
