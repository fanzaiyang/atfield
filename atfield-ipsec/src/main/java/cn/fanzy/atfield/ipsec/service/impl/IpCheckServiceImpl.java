package cn.fanzy.atfield.ipsec.service.impl;

import cn.fanzy.atfield.ipsec.bean.IpStorageBean;
import cn.fanzy.atfield.ipsec.exception.IpInvalidException;
import cn.fanzy.atfield.ipsec.service.IpCheckService;
import cn.fanzy.atfield.ipsec.util.IpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * IP 检查服务实现
 *
 * @author fanzaiyang
 * @date 2024/07/12
 */
@Slf4j
@RequiredArgsConstructor
public class IpCheckServiceImpl implements IpCheckService {

    @Override
    public void check(String clientIp, IpStorageBean storage) {

        log.info("::IpCheck::clientIp:{},storage:{}", clientIp, JSONUtil.toJsonStr(storage));
        List<String> clientIpList = StrUtil.split(clientIp, ",")
                .stream()
                .map(String::trim)
                .distinct()
                .toList();
        // 拒绝优先,检查ip是否在拒绝访问名单中
        if (CollUtil.isNotEmpty(storage.getDeniedIpList())) {
            for (String clientIpStr : clientIpList) {
                boolean contains = IpUtil.isContains(clientIpStr, storage.getDeniedIpList());
                if (contains) {
                    throw new IpInvalidException("5001", "IP被拒绝访问！" + clientIp);
                }
            }
        }
        // 检查ip是否在允许访问名单中
        if (CollUtil.isNotEmpty(storage.getAllowedIpList())) {
            boolean isContains = false;
            for (String clientIpStr : clientIpList) {
                boolean contains = IpUtil.isContains(clientIpStr, storage.getAllowedIpList());
                if (contains) {
                    isContains = true;
                    break;
                }
            }
            if (!isContains) {
                throw new IpInvalidException("5002", "IP不允许访问！" + clientIp);
            }
        }
    }
}
