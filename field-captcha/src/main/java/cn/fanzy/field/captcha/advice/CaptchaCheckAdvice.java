package cn.fanzy.field.captcha.advice;

import cn.fanzy.field.captcha.CaptchaService;
import cn.fanzy.field.captcha.annotation.CaptchaCheck;
import cn.fanzy.field.captcha.enums.CaptchaType;
import cn.fanzy.field.captcha.property.CaptchaProperty;
import cn.fanzy.field.captcha.util.CaptchaTypeUtil;
import cn.fanzy.field.core.utils.AdviceUtil;
import cn.fanzy.field.core.utils.ParamUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * captcha检查建议
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@EnableConfigurationProperties({CaptchaProperty.class})
public class CaptchaCheckAdvice {

    private final CaptchaService captchaService;
    private final CaptchaProperty property;

    @Pointcut("@annotation(cn.fanzy.field.captcha.annotation.CaptchaCheck)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        CaptchaCheck annotation = AdviceUtil.getAnnotation(jp, CaptchaCheck.class);
        if (annotation == null) {
            return;
        }
        CaptchaType captchaType = annotation.value();
        String codedKey = annotation.codeKey();
        String codeValue = annotation.codeValue();
        if (StrUtil.isBlank(codedKey)) {
            codedKey = CaptchaTypeUtil.getCodeKey(captchaType, property);
        }
        if (StrUtil.isBlank(codeValue)) {
            codeValue = CaptchaTypeUtil.getCodeValue(captchaType, property);
        }
        Object target = ParamUtil.getParamValue(codedKey);
        Object code = ParamUtil.getParamValue(codeValue);
        captchaService.verify(captchaType, target == null ? null : target.toString(),
                code == null ? null : code.toString());
    }
}
