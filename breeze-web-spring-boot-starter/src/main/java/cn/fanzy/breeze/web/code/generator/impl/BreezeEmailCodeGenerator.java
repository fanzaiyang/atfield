package cn.fanzy.breeze.web.code.generator.impl;

import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.model.BreezeEmailCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.utils.HttpUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

@AllArgsConstructor
public class BreezeEmailCodeGenerator implements BreezeCodeGenerator<BreezeEmailCode> {
    @Override
    public BreezeEmailCode generate(ServletWebRequest servletWebRequest, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        String code = RandomStringUtils.random(sms.getLength(), sms.getContainLetter(), sms.getContainNumber());
        return new BreezeEmailCode(code, sms.getRetryCount() == null ? properties.getRetryCount() : sms.getRetryCount(),sms.getExpireIn());
    }

    @Override
    public String generateKey(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        return HttpUtil.extract(request,sms.getCodeKey())+"";
    }

    @Override
    public String getCodeInRequest(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.SmsCodeProperties sms = properties.getSms();
        return HttpUtil.extract(request,sms.getCodeValue())+"";
    }
}
