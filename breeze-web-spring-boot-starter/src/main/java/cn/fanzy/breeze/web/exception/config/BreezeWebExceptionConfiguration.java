package cn.fanzy.breeze.web.exception.config;

import cn.fanzy.breeze.core.exception.CustomException;
import cn.fanzy.breeze.core.utils.RegexUtil;
import cn.fanzy.breeze.web.exception.handler.BreezeExceptionHandler;
import cn.fanzy.breeze.web.exception.properties.BreezeWebExceptionProperties;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.utils.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * 微风web异常配置
 *
 * @author fanzaiyang
 * @date 2022-08-18
 */
@Slf4j
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@RestControllerAdvice
@Import(BreezeExceptionHandlerConfiguration.class)
@EnableConfigurationProperties({BreezeWebExceptionProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.exception", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeWebExceptionConfiguration {
    private final BreezeExceptionHandler handler;
    /**
     * 400 - Bad Request
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "参数解析失败")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},错误的请求,失败的原因为：{}", ssid, e.getMessage())
                , e);
        handler.after(e);
        return response;
    }

    /**
     * 400 - Bad Request
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "参数不符合要求")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{}，参数解析失败,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 405 - Method Not Allowed
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                               HttpRequestMethodNotSupportedException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.METHOD_NOT_ALLOWED.value(),
                StrUtil.blankToDefault(e.getMessage(), "不支持当前请求方法")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},不支持当前请求方法,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;

    }

    /**
     * 415 - Unsupported Media Type
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                           HttpMediaTypeNotSupportedException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.METHOD_NOT_ALLOWED.value(),
                StrUtil.blankToDefault(e.getMessage(), "不支持当前媒体类型")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},不支持当前媒体类型,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NullPointerException.class)
    public Object handleNullPointerException(HttpServletRequest request, NullPointerException e) {
        String ssid = this.getRequestId(request);
        e.printStackTrace();
        JsonContent<String> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                StrUtil.blankToDefault(e.getMessage(), "逻辑发生空指针异常！"), ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,失败的原因为空指针异常!", ssid), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServletException.class)
    public Object handleServletException(HttpServletRequest request, ServletException e) {
        String ssid = this.getRequestId(request);
        String msg = e.getMessage();
        JsonContent<String> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                RegexUtil.containChinese(msg) ? msg : "请求失败", ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,失败的原因为：{}", ssid, msg), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IOException.class)
    public Object handleIoException(HttpServletRequest request, IOException e) {
        String ssid = this.getRequestId(request);
        String msg = e.getMessage();
        JsonContent<String> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                RegexUtil.containChinese(msg) ? msg : "IO操作失败", ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,失败的原因为：{}", ssid, msg), e);
        handler.after(e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingServletRequestParameterException(HttpServletRequest request,
                                                                MissingServletRequestParameterException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "请求参数有误")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求参数有误,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(HttpServletRequest request,
                                                            MethodArgumentTypeMismatchException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "请求参数有误")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},方法参数有误,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 参数验证异常
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object handle(HttpServletRequest request, ValidationException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "非法参数"), ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},参数校验有误,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 参数验证异常
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object handle(HttpServletRequest request, ConstraintViolationException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.BAD_REQUEST.value(),
                StrUtil.blankToDefault(e.getMessage(), "非法参数")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},参数约束有误,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 数组越界 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Object handleIndexOutOfBoundsException(HttpServletRequest request, IndexOutOfBoundsException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                StrUtil.blankToDefault(e.getMessage(), "未查询到对应的数据")
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,出现数组越界,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - 自定义异常
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CustomException.class)
    public Object handleCustomException(HttpServletRequest request, CustomException e) {
        String ssid = this.getRequestId(request);
        JsonContent<String> response = new JsonContent<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,失败的原因为：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - IllegalStateException
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public Object handleIllegalStateException(HttpServletRequest request, IllegalStateException e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.BAD_REQUEST.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{}，请求失败,拦截到未知异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Object handleException(HttpServletRequest request, SQLSyntaxErrorException e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到SQLSyntaxErrorException异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SQLException.class)
    public Object handleException(HttpServletRequest request, SQLException e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到SQLException异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }


    /**
     * 404 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleException(HttpServletRequest request, NoHandlerFoundException e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.NOT_FOUND.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到未找到处理程序异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 处理valid验证参数问题
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonContent<Object> processException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String ssid = this.getRequestId(request);
        if (e.getBindingResult().getFieldErrors().size() > 0) {
            String field = e.getBindingResult().getFieldErrors().get(0).getField();
            String defaultMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到参数校验异常：{}", ssid, e.getMessage()), e);
            return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), defaultMessage);
        }
        handler.after(e);
        return JsonContent.error(e.getMessage());
    }

    /**
     * 处理文件上传异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public JsonContent<Object> processException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        String ssid = this.getRequestId(request);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到文件上传超过限制异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
    }

    /**
     * 500 - IllegalStateException
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到运行时异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 500 - Internal Server Error
     *
     * @param request HttpServletRequest
     * @param e       希望捕获的异常
     * @return 发生异常捕获之后的响应
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        String ssid = this.getRequestId(request);
        JsonContent<Object> response = new JsonContent<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
                , ExceptionUtil.getErrorLineNumber3(e.getStackTrace()));
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到未知异常：{}", ssid, e.getMessage()), e);
        handler.after(e);
        return response;
    }

    /**
     * 获取请求的id
     *
     * @param request HttpServletRequest
     * @return 请求的ID
     */
    private String getRequestId(HttpServletRequest request) {
        return StrUtil.format("[{}][{}]", request.getMethod(), request.getRequestURI());
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <全局异常拦截> 相关的配置。");
    }
}
