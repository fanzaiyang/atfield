package cn.fanzy.atfield.upload.v2.service;

import cn.fanzy.atfield.upload.v2.property.AttachUploadProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 附件上传服务
 *
 * @author fanzaiyang
 * @date 2025/04/25
 */
@Slf4j
@RequiredArgsConstructor
public class AttachUploadService {

    private final AttachUploadProperty property;

    /**
     * 上传
     *
     * @param inputStream 文件流
     * @param objectName  对象名称
     * @param fileName    文件名
     * @param contentType 内容类型
     */
    void upload(InputStream inputStream, String objectName, String fileName, String contentType) {
        log.debug("附件上传::objectName:{}, fileName:{}, contentType:{}", objectName, fileName, contentType);

    }
}
