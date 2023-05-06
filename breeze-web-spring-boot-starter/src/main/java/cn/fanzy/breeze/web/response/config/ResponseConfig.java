package cn.fanzy.breeze.web.response.config;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.response.annotation.ResponseWrapper;
import cn.fanzy.breeze.web.response.properties.ResponseWrapperProperties;
import cn.fanzy.breeze.web.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 响应配置
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
@Slf4j
@ControllerAdvice
@EnableConfigurationProperties({ResponseWrapperProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.response.wrapper", name = {"enable"}, havingValue = "true")
public class ResponseConfig implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 注解优先
        ResponseWrapper annotation = returnType.getMethodAnnotation(ResponseWrapper.class);
        if (annotation!= null) {
            return true;
        }
        // Swagger的请求忽略
        PathMatcher matcher = new AntPathMatcher();
        return BreezeConstants.SWAGGER_LIST.stream().noneMatch(uri -> matcher.match(uri, Objects.requireNonNull(SpringUtils.getRequestUri())));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof JsonContent) {
            return body;
        }

        return JsonContent.success(body);
    }
}
