package cn.fanzy.infra.captcha.advice;

import cn.fanzy.infra.captcha.CaptchaService;
import cn.fanzy.infra.captcha.annotation.CaptchaCheck;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.util.CaptchaTypeUtil;
import cn.fanzy.infra.core.utils.AdviceUtil;
import cn.fanzy.infra.core.utils.ParamUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.context.request.ServletWebRequest;

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

    @Pointcut("@annotation(cn.fanzy.infra.captcha.annotation.CaptchaCheck)")
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
