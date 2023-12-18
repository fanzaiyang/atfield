package cn.fanzy.spider.file.utils;

import java.util.Map;

/**
 * RESP
 *
 * @author fanzaiyang
 * @date 2023/12/18
 */
public class Resp {

    public static Map<String, Object> convert(Object data) {
        return Map.of(
                "code", "200",
                "message", "操作成功！",
                "data", data,
                "success", true);
    }
}
