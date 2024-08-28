package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.spring.RequestWrapper;
import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 参数工具类
 *
 * @author fanzaiyang
 * @date 2023/09/07
 * @since 17
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

    /**
     * 获取参数值
     * <pre>
     *     1. 请求参数
     *     2. 请求头
     *     3. 请求体body
     * </pre>
     *
     * @param request 要求
     * @param key     钥匙
     * @return {@link Object}
     */
    public static Object getParamValue(ServletWebRequest request, String key) {

        // 如果没有获取到就从请求参数里获取
        String value = request.getParameter(key);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] strings = parameterMap.get(key);
        if (strings != null && strings.length > 0) {
            return strings[0];
        }
        // 请求头中获取
        value = request.getHeader(key);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        // 请求body中获取
        if (SpringUtils.isJson(request.getRequest())) {
            String jsonParam = new RequestWrapper(request.getRequest()).getBodyString();
            if (!JSONUtil.isTypeJSONObject(jsonParam)) {
                return null;
            }
            JSONObject entries = JSONUtil.parseObj(jsonParam);
            return entries.getObj(key);
        }

        return null;

    }

    /**
     * 获取参数值
     * <pre>
     *     1. 请求参数
     *     2. 请求头
     *     3. 请求体body
     * </pre>
     *
     * @param key 钥匙
     * @return {@link Object}
     */
    public static Object getParamValue(String key) {
        return getParamValue(new ServletWebRequest(SpringUtils.getRequest()), key);

    }

    /**
     * 从请求中得到上传的文件Map
     *
     * @param request 请求
     * @return Map
     */
    public static Map<String, MultipartFile> getMultipartFileMap(HttpServletRequest request) {
        Assert.isTrue(request instanceof MultipartHttpServletRequest, "请求方式不是MultipartHttpServletRequest！");
        assert request instanceof MultipartHttpServletRequest;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if (fileMap.isEmpty()) {
            throw new RuntimeException("请至少上传1个文件！");
        }
        return fileMap;
    }

    /**
     * 从请求中得到上传的文件列表
     *
     * @param request 请求
     * @return {@link List} {@link MultipartFile}
     */
    public static List<MultipartFile> getMultipartFileList(HttpServletRequest request) {
        Map<String, MultipartFile> fileMap = getMultipartFileMap(request);
        List<MultipartFile> fileList = new ArrayList<>();
        for (String key : fileMap.keySet()) {
            fileList.add(fileMap.get(key));
        }
        return fileList;
    }

    /**
     * 得到begin日期
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getBeginDate(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return valueList.get(0);
        }
        return null;
    }

    public static String getBeginTime(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return valueList.get(0).length() == 10 ? valueList.get(0) + " 00:00:00" : valueList.get(0);
        }
        return null;
    }

    public static String getEndTime(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return valueList.get(1).length() == 10 ? valueList.get(1) + " 23:59:59" : valueList.get(1);
        }
        return null;
    }
    public static Date getBeginTimeD(List<String> valueList) {
        String beginTime = getBeginTime(valueList);
        if(StrUtil.isNotBlank(beginTime)){
            return DateUtil.parse(beginTime,"yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
    public static Date getEndTimeD(List<String> valueList) {
        String endTime = getEndTime(valueList);
        if(StrUtil.isNotBlank(endTime)){
            return DateUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
    /**
     * 得到结束日期
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getEndDate(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return valueList.get(1);
        }
        return null;
    }

    /**
     * 得到日期范围地图
     *
     * @param valueList 值列表
     * @return {@link Map }<{@link String }, {@link Object }>
     */
    public static Map<String, Object> getDateRangeMap(List<String> valueList) {
        Map<String, Object> map = new HashMap<>();
        if (valueList != null && CollUtil.size(valueList) == 2) {
            map.put("beginDate", valueList.get(0));
            map.put("endDate", valueList.get(1));
        }
        return map;
    }

    public static Map<String, Object> getDateTimeRangeMap(List<String> valueList) {
        Map<String, Object> map = new HashMap<>();
        if (valueList != null && CollUtil.size(valueList) == 2) {
            map.put("beginDate", valueList.get(0).length() == 10 ? valueList.get(0) + " 00:00:00" : valueList.get(0));
            map.put("endDate", valueList.get(1).length() == 10 ? valueList.get(1) + " 23:59:59" : valueList.get(1));
        }
        return map;
    }
}
