package cn.fanzy.atfield.tlog.print.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.tlog.print.context.LogRecordContext;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * sp el utils
 *
 * @author Administrator
 * @date 2024/10/16
 */
@Component
public class SpElUtils {
    // 表达式解析模板，在 {{  }} 中的内容，会被当作 SpEL 表达式进行解析
    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{{", "}}");
    // SpEL解析器
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();


    public static StandardEvaluationContext getEvaluationContext(JoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 设置 ApplicationContext 到 Context 中，这样的话，可以通过 @beanname 表达式来访问 IOC 中的 Bean。
        context.setBeanResolver(new BeanFactoryResolver(SpringUtils.getApplicationContext()));

        // 把方法中的参数都设置到 context 中，使用参数名称作为 key。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            context.setVariable(parameterNames[i], joinPoint.getArgs()[i]);
        }

        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && !variables.isEmpty()) {
            context.setVariables(variables);
        }
        return context;
    }

    /**
     * 解析
     *
     * @param expression 表达
     * @param joinPoint  加入点
     * @return {@link String }
     */
    public static String parse(String expression, JoinPoint joinPoint) {
        try {
            if (!StrUtil.contains(expression, "{{")) {
                return expression;
            }
            return EXPRESSION_PARSER.parseExpression(expression, TEMPLATE_PARSER_CONTEXT)
                    .getValue(getEvaluationContext(joinPoint), String.class);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
