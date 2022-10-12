package cn.fanzy.breeze.web.safe.service;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.fanzy.breeze.web.safe.model.BreezeSafeInfo;
import cn.fanzy.breeze.web.safe.properties.BreezeSafeProperties;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Date;

@Slf4j
@AllArgsConstructor
public class BreezeSafeServiceImpl implements BreezeSafeService {
    private final BreezeCacheService cacheService;
    private final BreezeSafeProperties properties;
    private final BreezeCodeProcessor processor;

    @Override
    public void count(String loginId) {
        if (StrUtil.isBlank(loginId)) {
            log.warn("登录名不能为空！");
            return;
        }
        String key = getKey(loginId);
        // 错误次数
        Object obj = cacheService.get(key);
        BreezeSafeInfo safeInfo = new BreezeSafeInfo();
        if (obj != null) {
            safeInfo = (BreezeSafeInfo) obj;
        }
        safeInfo.setLoginId(loginId);
        safeInfo.setLoginIp(SpringUtils.getClientIp());
        // 次数+1
        safeInfo.setFailNum(safeInfo.getFailNum() + 1);
        // 更新时间
        safeInfo.setExpireTime(DateUtil.offsetSecond(new Date(), properties.getLoginTimeoutSecond()));
        cacheService.save(key, safeInfo, properties.getLoginTimeoutSecond());
    }

    @Override
    public void check(String loginId, BreezeSafe breezeSafe) {
        if (StrUtil.isBlank(loginId)) {
            log.warn("登录名不能为空！");
            return;
        }
        String key = getKey(loginId);
        // 错误次数
        Object obj = cacheService.get(key);
        BreezeSafeInfo safeInfo = new BreezeSafeInfo();
        if (obj != null) {
            safeInfo = (BreezeSafeInfo) obj;
        }
        if (safeInfo.getFailNum() >= properties.getLoginFailedMaxNum()) {
            throw new RuntimeException(StrUtil.format("该账号因重试次数太多，而锁定，请{}后重试！", safeInfo.getDeadTime()));
        }
        // 校验验证码
        if (properties.isNeedCode() && safeInfo.getFailNum() >= properties.getLoginFailedShowCodeMaxNum()) {
            processor.validate(new ServletWebRequest(SpringUtils.getRequest()), breezeSafe.value());
        }
    }

    @Override
    public boolean isShowCode(String loginId) {
        if (StrUtil.isBlank(loginId)) {
            log.warn("登录名不能为空！");
            return false;
        }
        if (!properties.isNeedCode()) {
            return false;
        }
        String key = getKey(loginId);
        Object obj = cacheService.get(key);
        BreezeSafeInfo safeInfo = new BreezeSafeInfo();
        if (obj != null) {
            safeInfo = (BreezeSafeInfo) obj;
        }
        return safeInfo.getFailNum() >= properties.getLoginFailedShowCodeMaxNum();
    }

    private String getKey(String loginId) {
        String key = properties.getLoginFailedPrefix() + loginId;
        if (properties.isSingleIp()) {
            key += ":" + SpringUtils.getClientIp();
        }
        return key;
    }

    @Override
    public String getErrorMsg(String loginId) {
        String key = getKey(loginId);
        Object obj = cacheService.get(key);
        BreezeSafeInfo safeInfo = new BreezeSafeInfo();
        if (obj != null) {
            safeInfo = (BreezeSafeInfo) obj;
        }
        if (safeInfo.getFailNum() >= properties.getLoginFailedMaxNum()) {
            return StrUtil.format("该账号因重试次数太多，而锁定，请{}后重试！", safeInfo.getDeadTime());
        }
        return StrUtil.format("当前可有「{}」次重试机会！", properties.getLoginFailedMaxNum() - safeInfo.getFailNum());
    }
}
