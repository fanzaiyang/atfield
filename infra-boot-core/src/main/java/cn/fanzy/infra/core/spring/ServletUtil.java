package cn.fanzy.infra.core.spring;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * servlet工具类
 *
 * @author fanzaiyang
 * @date 2023/11/30
 * @since 2021/09/06
 */
@Slf4j
public class ServletUtil extends JakartaServletUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 携带指定的信息重定向到指定的地址
     *
     * @param url 指定的重定向地址
     */
    public synchronized static void redirect(String url) throws IOException {
        SpringUtils.getResponse().sendRedirect(url);
    }

    /**
     * 将指定的信息按照json格式输出到指定的响应
     *
     * @param response 响应
     * @param data     输出的指定信息
     */
    public synchronized static void out(HttpServletResponse response, Object data) {
        response.setStatus(HttpStatus.OK.value());
        // 允许跨域访问的域，可以是一个域的列表，也可以是通配符"*"
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许使用的请求方法，以逗号隔开
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 是否允许请求带有验证信息，
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(MAPPER.writeValueAsString(data));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.info("返回响应数据时出现问题，出现问题的原因为 {}", e.getMessage());
        }
    }

    /**
     * 打印请求中携带的查询参数和请求头信息
     *
     * @param request HttpServletRequest
     */
    public static void stack(HttpServletRequest request) {
        log.info("\r\n");
        log.info("==start  用户请求的请求参数中包含的信息为 query start ===");
        Map<String, String[]> params = request.getParameterMap();
        if (null != params) {
            params.forEach((k, v) -> log.debug("请求参数中的参数名字为 {},对应的值为 {}", k, String.join(" , ", v)));
        }
        log.info("==end  用户请求的请求参数中包含的信息为  query end ===");
        log.info("==start  用户请求的请求头中包含的信息为 header start ===");
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String name = e.nextElement();
            log.debug("请求头的名字为 {},对应的值为 {}", name, request.getHeader(name));
        }
        log.info("==end  用户请求的请求头中包含的信息为 header end ===");
        log.info("\r\n");
    }

    /**
     * 下载
     *
     * @param file     文件
     * @param response 响应
     * @throws IOException ioexception
     */
    public static void download(File file, HttpServletResponse response) throws IOException {
        download(file.getName(), IoUtil.toStream(file), response);
    }

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param file     文件
     * @param response 响应
     * @throws IOException ioexception
     */
    public static void download(String fileName, File file, HttpServletResponse response) throws IOException {
        download(StrUtil.blankToDefault(fileName, file.getName()), IoUtil.toStream(file), response);
    }

    /**
     * 下载
     *
     * @param fileName    文件名称
     * @param inputStream 输入流
     * @param response    响应
     * @throws IOException ioexception
     */
    public static void download(String fileName, InputStream inputStream, HttpServletResponse response) throws IOException {
        // 设置强制下载打开
        response.setContentType("application/force-download");
        // 文件名乱码, 使用new String() 进行反编码
        String fName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;fileName=" + fName);
        IoUtil.copy(inputStream, response.getOutputStream());
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
     * 获取请求参数
     *
     * @param request 要求
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>(1);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            String[] paramArr = parameterMap.get(key);
            if (paramArr != null && paramArr.length == 1) {
                params.put(key, paramArr[0]);
                continue;
            }
            params.put(key, paramArr);
        }
        if (SpringUtils.isJson(request)) {
            String jsonParam = new RequestWrapper(request).getBodyString();
            if (JSONUtil.isTypeJSON(jsonParam)) {
                if (JSONUtil.isTypeJSONArray(jsonParam)) {
                    params.put("body", JSONUtil.parseArray(jsonParam));
                } else if (JSONUtil.isTypeJSONObject(jsonParam)) {
                    JSONObject obj = JSONUtil.parseObj(jsonParam);
                    for (Map.Entry<String, Object> entry : obj) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    params.put("unknown", jsonParam);
                }
            }
        }
        return params;

    }
}
