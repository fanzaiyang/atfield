package cn.fanzy.infra.ip.service.impl;

import cn.fanzy.infra.ip.bean.IpStorageBean;
import cn.fanzy.infra.ip.exception.IpInvalidException;
import cn.fanzy.infra.ip.service.IpCheckService;
import cn.fanzy.infra.ip.service.IpStorageService;
import cn.fanzy.infra.ip.util.IpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class IpCheckServiceImpl implements IpCheckService {
    private final IpStorageService ipStorageService;


    @Override
    public void check(String clientIp, IpStorageBean storage) {
        if (storage == null) {
            storage = ipStorageService.getIpStorage();
        }
        // 拒绝优先,检查ip是否在拒绝访问名单中
        if (IpUtil.isContains(clientIp, storage.getDeniedIpList())) {
            throw new IpInvalidException("5001", "IP被拒绝访问！");
        }
        // 检查ip是否在允许访问名单中
        if (!IpUtil.isContains(clientIp, storage.getAllowedIpList())) {
            throw new IpInvalidException("5002", "IP不在允许访问名单中！");
        }
    }
}
