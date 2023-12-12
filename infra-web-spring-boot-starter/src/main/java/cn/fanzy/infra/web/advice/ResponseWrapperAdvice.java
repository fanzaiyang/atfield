package cn.fanzy.infra.web.advice;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.core.utils.InfraConstants;
import cn.fanzy.infra.web.json.model.Json;
import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import cn.fanzy.infra.web.response.property.ResponseWrapperProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 响应配置
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@EnableConfigurationProperties({ResponseWrapperProperty.class})
@ConditionalOnProperty(prefix = "infra.web.response.wrapper", name = {"enable"}, havingValue = "true")
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;
    private final ResponseWrapperProperty properties;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 注解优先
        ResponseWrapper annotation = returnType.getMethodAnnotation(ResponseWrapper.class);
        if (annotation != null) {
            return true;
        }
        if (properties.getGlobal() != null && properties.getGlobal()) {
            // Swagger的请求忽略
            log.info(SpringUtils.getRequestUri());
            PathMatcher matcher = new AntPathMatcher();
            return InfraConstants.SWAGGER_LIST.stream()
                    .noneMatch(uri -> matcher.match(uri, Objects.requireNonNull(SpringUtils.getRequestUri())));
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        ResponseWrapper annotation = returnType.getMethodAnnotation(ResponseWrapper.class);
        if (annotation == null) {
            return body;
        }

        if (annotation.value().isInstance(body)) {
            return body;
        }
        // String类型的返回值需要特殊处理,是使用了StringHttpMessageConverter转换器，无法转换为Json格式。
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(Json.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Json.ok(body);
    }
}
