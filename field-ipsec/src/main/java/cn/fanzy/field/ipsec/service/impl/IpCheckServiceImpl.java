package cn.fanzy.field.ipsec.service.impl;

import cn.fanzy.field.ipsec.bean.IpStorageBean;
import cn.fanzy.field.ipsec.exception.IpInvalidException;
import cn.fanzy.field.ipsec.service.IpCheckService;
import cn.fanzy.field.ipsec.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class IpCheckServiceImpl implements IpCheckService {

    @Override
    public void check(String clientIp, IpStorageBean storage) {
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
