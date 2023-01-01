package cn.fanzy.breeze.web.code.generator.impl;

import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.model.BreezeSmsCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fanzaiyang
 */
@AllArgsConstructor
public class BreezeSmsCodeGenerator implements BreezeCodeGenerator<BreezeSmsCode> {
    @Override
    public BreezeSmsCode generate(ServletWebRequest servletWebRequest, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        String code = RandomStringUtils.random(sms.getLength(), sms.getContainLetter(), sms.getContainNumber());
        return new BreezeSmsCode(code, sms.getRetryCount() == null ? properties.getRetryCount() : sms.getRetryCount(), sms.getExpireIn());
    }

    @Override
    public String generateKey(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        Object key = HttpUtil.extract(request, sms.getCodeKey());
        Assert.notNull(key, "请在请求参数添加「{}」参数。", sms.getCodeKey());
        return key + "";
    }

    @Override
    public String getCodeInRequest(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        return HttpUtil.extract(request, sms.getCodeValue()) + "";
    }
}
