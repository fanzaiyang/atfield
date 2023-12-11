package cn.fanzy.infra.core.utils;

import cn.fanzy.infra.core.spring.RequestWrapper;
import cn.fanzy.infra.core.spring.SpringUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    /**
     * 获取参数值
     * <pre>
     *     1. 请求参数
     *     2. 请求头
     *     3. 请求体body
     * </pre>
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
    public static Object getParamValue(String key) {
        return getParamValue(new ServletWebRequest(SpringUtils.getRequest()),key);

    }
    /**
     * 从请求中得到上传的文件Map
     *
     * @param request 请求
     * @return Map
     */
    public static Map<String, MultipartFile> getMultipartFileMap(HttpServletRequest request) {
        Assert.isTrue(request instanceof MultipartHttpServletRequest, "请求方式不是MultipartHttpServletRequest！");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        assert multipartRequest != null;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if (fileMap.size() < 1) {
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

}
