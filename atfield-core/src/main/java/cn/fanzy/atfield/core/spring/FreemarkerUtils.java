package cn.fanzy.atfield.core.spring;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

/**
 * freemarker 实用程序
 * <pre>
 *     需要引入spring-boot-starter-freemarker
 * </pre>
 *
 * @author fanzaiyang
 * @date 2024/09/24
 */
@Slf4j
@Component
@ConditionalOnClass(value = {Template.class, FreeMarkerConfigurer.class})
public class FreemarkerUtils extends FreeMarkerTemplateUtils {
    private static FreeMarkerConfigurer configurer;

    /**
     * 生成 HTML
     *
     * @param templateName 模板名称:demo.ftl
     * @param context      上下文
     * @return {@link String }
     */
    public static String generateHtml(String templateName, Map<String, Object> context) {
        try {
            Template template = configurer.getConfiguration().getTemplate(templateName);
            return processTemplateIntoString(template, context);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        configurer = SpringUtils.getBean(FreeMarkerConfigurer.class);
    }
}
