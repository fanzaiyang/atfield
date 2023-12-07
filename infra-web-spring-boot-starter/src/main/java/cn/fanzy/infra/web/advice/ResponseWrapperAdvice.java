package cn.fanzy.infra.web.advice;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.core.utils.InfraConstants;
import cn.fanzy.infra.web.json.model.R;
import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import cn.fanzy.infra.web.response.properties.ResponseWrapperProperties;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
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
@EnableConfigurationProperties({ResponseWrapperProperties.class})
@ConditionalOnProperty(prefix = "infra.web.response.wrapper", name = {"enable"}, havingValue = "true")
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 注解优先
        ResponseWrapper annotation = returnType.getMethodAnnotation(ResponseWrapper.class);
        if (annotation != null) {
            return true;
        }
        // Swagger的请求忽略
        PathMatcher matcher = new AntPathMatcher();
        return InfraConstants.SWAGGER_LIST.stream()
                .noneMatch(uri -> matcher.match(uri, Objects.requireNonNull(SpringUtils.getRequestUri())));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return body;
        }
        if (body instanceof R<?>) {
            return body;
        }
        // String类型的返回值需要特殊处理,是使用了StringHttpMessageConverter转换器，无法转换为Json格式。
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(R.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return R.ok(body);
    }
}