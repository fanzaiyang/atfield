package cn.fanzy.breeze.web.code.processor.impl;

import cn.fanzy.breeze.web.code.enums.IBreezeCodeTypeEnum;
import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Slf4j
public class BreezeCodeDefaultProcessor implements BreezeCodeProcessor {

    private final Map<String, BreezeCodeGenerator> codeGenerators;
    private final Map<String, BreezeCodeSender> codeSenders;
    private final BreezeCodeRepository repository;
    private final BreezeCodeProperties codeProperties;

    public BreezeCodeDefaultProcessor(Map<String, BreezeCodeGenerator> codeGenerators, Map<String, BreezeCodeSender> codeSenders, BreezeCodeRepository repository, BreezeCodeProperties codeProperties) {
        this.codeGenerators = codeGenerators;
        this.codeSenders = codeSenders;
        this.repository = repository;
        this.codeProperties = codeProperties;
    }

    @Override
    public BreezeCode create(ServletWebRequest request, IBreezeCodeTypeEnum codeType) {
        String key = generator(codeType).generateKey(request, codeProperties);
        return create(request,key,codeType);
    }

    @Override
    public BreezeCode create(ServletWebRequest request, String key, IBreezeCodeTypeEnum codeType) throws ValidateException {
        // 生成验证码
        BreezeCode validateCode = generator(codeType).generate(request, codeProperties);
        log.info("【公共组件】生成的验证码的类型为 {}, 标识符为{}，内容为 {}", codeType, key, validateCode);
        // 保存验证码
        repository.save(key, new BreezeCode(validateCode.getCode(), validateCode.getMaxRetryCode(), validateCode.getExpireTimeInSeconds()));
        return validateCode;
    }

    @Override
    public BreezeCode createAndSend(ServletWebRequest request, IBreezeCodeTypeEnum codeType) throws ValidateException {
        // 验证码的唯一标识符
        String key = generator(codeType).generateKey(request, codeProperties);
        return this.createAndSend(request, key, codeType);
    }

    @Override
    public BreezeCode createAndSend(ServletWebRequest request, String key, IBreezeCodeTypeEnum codeType) {
        BreezeCode validateCode = this.create(request, key, codeType);
        // 发送验证码
        this.codeSender(codeType).send(request, key, validateCode);
        return validateCode;
    }

    @Override
    public void validate(ServletWebRequest request, IBreezeCodeTypeEnum codeType) {
        // 验证码生成器
        BreezeCodeGenerator codeGenerator = generator(codeType);
        // 验证码的唯一标识符
        String key = codeGenerator.generateKey(request, codeProperties);
        // 获取请求中的验证码
        String codeInRequest = codeGenerator.getCodeInRequest(request, codeProperties);
        this.validate(key, codeInRequest);
    }

    @Override
    public void validate(String key, String codeInRequest) {
        log.info("【公共组件】从请求中获取的验证码的内容的为 {} ", codeInRequest);
        Assert.notBlank(codeInRequest, "验证码不能为空!");
        /**
         * 获取到存储的验证码
         */
        BreezeCode codeInSession = repository.get(key);
        log.info("【公共组件】从系统中获取到存储的验证码为 {}", codeInSession);
        Assert.notNull(codeInSession, "验证码不能为空!");
        Assert.isTrue(!codeInSession.isExpired(), "验证码已过期!");
        if (!StrUtil.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            // 添加一次重试次数
            codeInSession.setRetryCount(codeInSession.getRetryCount() + 1);
            repository.save(key, codeInSession);
            if (codeInSession.getRetryCount() >= codeInSession.getMaxRetryCode()) {
                repository.remove(key);
                throw new RuntimeException("验证码不匹配，请重新生成验证码后重试!");
            }
            throw new RuntimeException("验证码不匹配!");
        }
        if (BooleanUtils.isNotFalse(codeProperties.getDeleteOnSuccess())) {
            // 移除验证码
            repository.remove(key);
        }
    }


    private BreezeCodeGenerator generator(IBreezeCodeTypeEnum codeType) {
        // 获取到对应的验证码生成器
        BreezeCodeGenerator codeGenerator = codeGenerators.get(codeType.getGenerator());
        Assert.notNull(codeGenerator, "未找到验证码生成器！");
        return codeGenerator;
    }

    private BreezeCodeSender codeSender(IBreezeCodeTypeEnum codeType) {
        BreezeCodeSender codeSender = codeSenders.get(codeType.getSender());
        Assert.notNull(codeSender, "未找到验证码发送器！");
        return codeSender;
    }

}
