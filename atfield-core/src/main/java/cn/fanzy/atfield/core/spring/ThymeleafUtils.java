package cn.fanzy.atfield.core.spring;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * thymeleaf工具类
 * <pre>
 *     需要引入spring-boot-starter-thymeleaf
 * </pre>
 *
 * @author fanzaiyang
 * @date 2024/09/24
 */
@Slf4j
@Component
@ConditionalOnClass(value = {TemplateEngine.class})
public class ThymeleafUtils {
    private static TemplateEngine templateEngine;

    /**
     * 生成 HTML
     *
     * @param templateName 模板名称
     * @param params       参数
     * @return {@link String }
     */
    public static String generateHtml(String templateName, Map<String, Object> params) {
        Context context = new Context();
        context.setVariables(params);
        return templateEngine.process(templateName, context);
    }

    @PostConstruct
    public void init() {
        templateEngine = SpringUtils.getBean(TemplateEngine.class);
    }
}
