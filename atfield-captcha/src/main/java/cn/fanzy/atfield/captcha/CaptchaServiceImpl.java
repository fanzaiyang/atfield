package cn.fanzy.atfield.captcha;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.creator.CaptchaCreatorService;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import cn.fanzy.atfield.captcha.exception.CaptchaErrorException;
import cn.fanzy.atfield.captcha.exception.CaptchaExpiredException;
import cn.fanzy.atfield.captcha.exception.NoCaptchaException;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import cn.fanzy.atfield.captcha.sender.CaptchaSenderService;
import cn.fanzy.atfield.captcha.storage.CaptchaStorageService;
import cn.fanzy.atfield.captcha.util.CaptchaTypeUtil;
import cn.fanzy.atfield.core.utils.ParamUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.Optional;

/**
 * 验证码服务实现
 *
 * @author fanzaiyang
 * @date 2024/02/01
 */
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
            throw new RuntimeException("【" + type.getCaptchaName() + "】生成器不存在！");
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
            throw new RuntimeException("【" + type.getCaptchaName() + "】发送器不存在！");
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
        Assert.notBlank(target, "【{}】发送的对象不能为空！", type.getCaptchaName());
        Assert.notBlank(code, "【{}】验证码不能为空！", type.getCaptchaName());
        CaptchaCode captchaCode = get(target);
        if (captchaCode == null) {
            throw new NoCaptchaException("-5001", "【" + type.getCaptchaName() + "】不存在或已过期！");
        }
        captchaCode.preVerify();
        // 次数+1
        captchaCode.setUseCountIncrease();
        captchaStorageService.save(target, captchaCode);
        if (property.isEqualsIgnoreCase()) {
            if (StrUtil.equalsIgnoreCase(code, captchaCode.getCode())) {
                captchaStorageService.delete(target);
            } else {
                throw new CaptchaErrorException("-5003", "【" + type.getCaptchaName() + "】输入错误！");
            }
        } else {
            if (code.equals(captchaCode.getCode())) {
                captchaStorageService.delete(target);
            } else {
                throw new CaptchaExpiredException("-5003", "【" + type.getCaptchaName() + "】输入错误！");
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
