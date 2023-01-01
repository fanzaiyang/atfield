package cn.fanzy.breeze.web.ip.service.impl;

import cn.fanzy.breeze.web.ip.properties.BreezeIpProperties;
import cn.fanzy.breeze.web.ip.service.BreezeIpGlobalCheckService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fanzaiyang
 */
@Slf4j
@AllArgsConstructor
public class BreezeIpDefaultGlobalCheckService implements BreezeIpGlobalCheckService {
    private final BreezeIpProperties properties;

    @Override
    public void handler(ServletWebRequest servletWebRequest) {
        String clientIp = SpringUtils.getClientIp(servletWebRequest.getRequest());
        // 1. 校验黑名单
        if (properties.getDeny() != null && properties.getDeny().length > 0 && ArrayUtil.contains(properties.getDeny(), clientIp)) {
            throw new RuntimeException(StrUtil.format("当前IP「{}」被拒绝访问！", clientIp));
        }
        // 1. 校验白名单
        if (properties.getAllowed() != null && properties.getAllowed().length > 0 && !ArrayUtil.contains(properties.getAllowed(), clientIp)) {
            throw new RuntimeException(StrUtil.format("当前IP「{}」被拒绝访问！", clientIp));
        }
    }
}
