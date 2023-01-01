package cn.fanzy.breeze.web.code.aop;

import cn.fanzy.breeze.web.code.annotation.BreezeCodeChecker;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author fanzaiyang
 */
@Aspect
@Configuration
@AllArgsConstructor
public class BreezeCodeCheckAop {
    private final BreezeCodeProperties properties;
    private final BreezeCodeProcessor processor;

    private final HttpServletRequest request;

    @Pointcut("@annotation(cn.fanzy.breeze.web.code.annotation.BreezeCodeChecker)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        BreezeCodeChecker codeChecker = method.getAnnotation(BreezeCodeChecker.class);
        // 白名单验证
        if (codeChecker.value() == null) {
            throw new RuntimeException("未配置验证码类型！");
        }
        BreezeCodeType codeType = codeChecker.value();
        String codeKey;
        String codeValue;
        switch (codeType.name()) {
            case "SMS":
                codeKey = properties.getSms().getCodeKey();
                codeValue = properties.getSms().getCodeValue();
                break;
            case "IMAGE":
                codeKey = properties.getImage().getCodeKey();
                codeValue = properties.getImage().getCodeValue();
                break;
            case "EMAIL":
                codeKey = properties.getEmail().getCodeKey();
                codeValue = properties.getEmail().getCodeValue();
                break;
            default:
                throw new RuntimeException("未知的验证码类型！");
        }
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Object codeKeyObj = HttpUtil.extract(servletWebRequest, codeKey);
        Assert.notNull(codeKeyObj, "未找到验证码的参数「{}」的值！", codeKey);
        Object codeValueObj = HttpUtil.extract(servletWebRequest, codeValue);
        Assert.notNull(codeValueObj, "未找到验证码的参数「{}」的值！", codeValue);
        processor.validate(codeKeyObj.toString(), codeValueObj.toString());
    }
}
