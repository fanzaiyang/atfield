package cn.fanzy.atfield.upload.utils;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * BREEZE 文件内容类型
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Getter
public enum BreezeFileContentType {
    DEFAULT("default", "application/octet-stream"),
    JPG("jpg", "image/jpeg"),
    TIFF("tiff", "image/tiff"),
    GIF("gif", "image/gif"),
    JFIF("jfif", "image/jpeg"),
    PNG("png", "image/png"),
    TIF("tif", "image/tiff"),
    ICO("ico", "image/x-icon"),
    JPEG("jpeg", "image/jpeg"),
    WBMP("wbmp", "image/vnd.wap.wbmp"),
    FAX("fax", "image/fax"),
    NET("net", "image/pnetvue"),
    JPE("jpe", "image/jpeg"),
    RP("rp", "image/vnd.rn-realpix");

    private final String fileType;

    private final String contentType;

    BreezeFileContentType(String fileType, String contentType) {
        this.fileType = fileType;
        this.contentType = contentType;
    }

    public static String getContentType(String fileType) {
        if (StrUtil.isEmpty(fileType)) {
            return DEFAULT.getContentType();
        }

        for (BreezeFileContentType value : BreezeFileContentType.values()) {
            if (fileType.equalsIgnoreCase(value.getFileType())) {
                return value.getContentType();
            }
        }
        return DEFAULT.getContentType();
    }

}
