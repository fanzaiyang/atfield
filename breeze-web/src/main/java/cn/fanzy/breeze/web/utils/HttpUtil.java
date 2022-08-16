package cn.fanzy.breeze.web.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yomahub.tlog.context.TLogContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * http工具
 *
 * @author fanzaiyang
 * @since 2021/09/06
 */
@Slf4j
public class HttpUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 携带指定的信息重定向到指定的地址
     *
     * @param url 指定的重定向地址
     * @throws IOException 重定向时出现异常
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
        log.debug("\r\n");
        log.debug("==start  用户请求的请求参数中包含的信息为 query start ===");
        Map<String, String[]> params = request.getParameterMap();
        if (null != params) {
            params.forEach((k, v) -> log.debug("请求参数中的参数名字为 {},对应的值为 {}", k, String.join(" , ", v)));
        }
        log.debug("==end  用户请求的请求参数中包含的信息为  query end ===");
        log.debug("==start  用户请求的请求头中包含的信息为 header start ===");
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String name = e.nextElement();
            log.debug("请求头的名字为 {},对应的值为 {}", name, request.getHeader(name));
        }
        log.debug("==end  用户请求的请求头中包含的信息为 header end ===");
        log.debug("\r\n");
    }

    /**
     * get请求id
     *
     * @return {@link String}
     */
    public static String getRequestId() {
        return TLogContext.getTraceId();
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

    public static Object extract(ServletWebRequest request, String key) {

        // 如果没有获取到就从请求参数里获取
        String value = request.getParameter(key);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        // 请求头中获取
        value = request.getHeader(key);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        throw new RuntimeException("请求参数中未包含验证码！");

    }

}
