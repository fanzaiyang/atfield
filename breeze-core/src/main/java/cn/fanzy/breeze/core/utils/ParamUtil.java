package cn.fanzy.breeze.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数工具类
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
public class ParamUtil {

    /**
     * 获取参数ascii
     *
     * @param map 地图
     * @return {@link String}
     */
    public static String getParamAscii(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        List<String> paramList = new ArrayList<>();
        for (String key : map.keySet()) {
            paramList.add(StrUtil.format("{}={}", key, JSONUtil.toJsonStr(map.get(key))));
        }
        String paramStr = paramList.parallelStream().sorted().collect(Collectors.joining("&"));
        return SecureUtil.md5(paramStr);
    }
}
