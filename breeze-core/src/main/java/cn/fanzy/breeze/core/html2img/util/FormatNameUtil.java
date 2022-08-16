package cn.fanzy.breeze.core.html2img.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 格式名称工具类
 *
 * @author fanzaiyang
 * @date 2021/08/26
 */
public class FormatNameUtil {
    public static Map<String, String> types = new HashMap<String, String>();
    private static final String DEFAULT_FORMAT = "png";

    static {
        types.put("gif", "gif");
        types.put("jpg", "jpg");
        types.put("jpeg", "jpg");
        types.put("png", "png");
    }

    public static String formatForExtension(String extension) {
        final String type = types.get(extension);
        if (type == null) {
            return DEFAULT_FORMAT;
        }
        return type;
    }

    public static String formatForFilename(String fileName) {
        final int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return DEFAULT_FORMAT;
        }
        final String ext = fileName.substring(dotIndex + 1);
        return formatForExtension(ext);
    }
}
