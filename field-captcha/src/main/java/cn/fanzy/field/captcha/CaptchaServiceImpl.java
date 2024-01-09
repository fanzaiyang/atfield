package cn.fanzy.field.captcha;

import cn.fanzy.field.captcha.bean.CaptchaCode;
import cn.fanzy.field.captcha.creator.CaptchaCreatorService;
import cn.fanzy.field.captcha.enums.ICaptchaType;
import cn.fanzy.field.captcha.exception.CaptchaErrorException;
import cn.fanzy.field.captcha.exception.CaptchaExpiredException;
import cn.fanzy.field.captcha.exception.NoCaptchaException;
import cn.fanzy.field.captcha.property.CaptchaProperty;
import cn.fanzy.field.captcha.sender.CaptchaSenderService;
import cn.fanzy.field.captcha.storage.CaptchaStorageService;
import cn.fanzy.field.captcha.util.CaptchaTypeUtil;
import cn.fanzy.field.core.utils.ParamUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final List<CaptchaCreatorService> creatorServiceList;
    private final List<CaptchaSenderService> senderServiceList;
    private final CaptchaStorageService captchaStorageService;
    private final CaptchaProperty property;

    @Override
    public CaptchaCode create(ICaptchaType type, String target) {
        Optional<CaptchaCreatorService> creatorService = creatorServiceList.stream()
                .filter(creator -> creator.isSupported(type)).findFirst();
        if (creatorService.isEmpty()) {
            throw new RuntimeException("该类型验证码的生成器不存在！");
        }
        CaptchaCode codeInfo = creatorService.get().generate(property);
        // 将验证码保存到缓存
        captchaStorageService.save(target, codeInfo);
        return codeInfo;
    }

    @Override
    public void send(ICaptchaType type, String target, CaptchaCode captchaCode) {
        Optional<CaptchaSenderService> senderService = senderServiceList.stream()
                .filter(sender -> sender.isSupported(type)).findFirst();
        if (senderService.isEmpty()) {
            throw new RuntimeException("该类型验证码的发送器不存在！");
        }
        senderService.get().send(target, captchaCode);
    }

    @Override
    public CaptchaCode get(String target) {
        return captchaStorageService.get(target, CaptchaCode.class);
    }

    @Override
    public CaptchaCode createAndSend(ICaptchaType type, String target) {
        return createAndSend(type, target, property);
    }

    @Override
    public CaptchaCode createAndSend(ICaptchaType type, String target, CaptchaProperty property) {
        CaptchaCode codeInfo = create(type, target);
        send(type, target, codeInfo);
        return codeInfo;
    }

    @Override
    public void verify(ICaptchaType type, String target, String code) {
        Assert.notBlank(target, "发送的对象不能为空！");
        Assert.notBlank(code, "验证码不能为空！");
        CaptchaCode captchaCode = get(target);
        if (captchaCode == null) {
            throw new NoCaptchaException("-5001", "验证码不存在或已过期！");
        }
        captchaCode.preVerify();
        // 次数+1
        captchaCode.setUseCountIncrease();
        captchaStorageService.save(target, captchaCode);
        if (property.isEqualsIgnoreCase()) {
            if (StrUtil.equalsIgnoreCase(code, captchaCode.getCode())) {
                captchaStorageService.delete(target);
            } else {
                throw new CaptchaErrorException("-5003", "验证码输入错误！");
            }
        } else {
            if (code.equals(captchaCode.getCode())) {
                captchaStorageService.delete(target);
            } else {
                throw new CaptchaExpiredException("-5003", "验证码输入错误！");
            }
        }
    }

    @Override
    public void verify(ICaptchaType captchaType, HttpServletRequest request) {
        // 从请求中获取参数
        String codedKey = CaptchaTypeUtil.getCodeKey(captchaType, property);
        String codeValue = CaptchaTypeUtil.getCodeValue(captchaType, property);
        Object target = ParamUtil.getParamValue(new ServletWebRequest(request), codedKey);
        Object code = ParamUtil.getParamValue(new ServletWebRequest(request), codeValue);
        verify(captchaType, target == null ? null : target.toString(), code == null ? null : code.toString());
    }
}
