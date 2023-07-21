package cn.fanzy.breeze.web.safe.utils;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.safe.model.BreezeSafeInfo;
import cn.fanzy.breeze.web.safe.properties.BreezeSafeProperties;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanzaiyang
 */
@Slf4j
public class BreezeSafeUtil {
    public static String getErrorMsg(String loginId) {
        if (StrUtil.isBlank(loginId)) {
            return "";
        }
        BreezeSafeProperties properties = SpringUtils.getBean(BreezeSafeProperties.class);
        BreezeCacheService cacheService = SpringUtils.getBean(BreezeCacheService.class);
        String key = getKey(properties, loginId);
        Object obj = cacheService.get(key);
        BreezeSafeInfo safeInfo = new BreezeSafeInfo();
        if (obj != null) {
            safeInfo = (BreezeSafeInfo) obj;
        }
        if (safeInfo.getFailNum() + 1 >= properties.getLoginFailedMaxNum()) {
            return StrUtil.format("该账号因重试次数太多，而锁定，请{}后重试！", safeInfo.getDeadTime());
        }
        return StrUtil.format("当前可有「{}」次重试机会！", properties.getLoginFailedMaxNum() - safeInfo.getFailNum());
    }

    public static String getKey(BreezeSafeProperties properties, String loginId) {
        String key = properties.getLoginFailedPrefix() + loginId;
        if (properties.isSingleIp()) {
            key += ":" + SpringUtils.getClientIp();
        }
        return key;
    }

    /**
     * 删除账号的安全限制
     *
     * @param loginId 登录id
     */
    public static void remove(String loginId){
        try {
            BreezeSafeProperties properties = SpringUtils.getBean(BreezeSafeProperties.class);
            BreezeCacheService cacheService = SpringUtils.getBean(BreezeCacheService.class);
            cacheService.remove(getKey(properties,loginId));
        }catch (Exception e){
            log.warn("删除「{}」安全认证失败！原因：{}",loginId,e.getMessage());
        }

    }
}
