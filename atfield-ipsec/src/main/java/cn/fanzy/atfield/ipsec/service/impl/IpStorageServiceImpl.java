package cn.fanzy.atfield.ipsec.service.impl;

import cn.fanzy.atfield.ipsec.bean.IpStorageBean;
import cn.fanzy.atfield.ipsec.property.IpProperty;
import cn.fanzy.atfield.ipsec.service.IpStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * ip存储服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/12
 */
@Slf4j
@RequiredArgsConstructor
public class IpStorageServiceImpl implements IpStorageService {
    private final IpProperty property;

    @Override
    public IpStorageBean getIpStorage() {
        return IpStorageBean.builder()
                .allowedIpList(property.getAllowedIps().length == 0 ? new HashSet<>() :
                        Arrays.stream(property.getAllowedIps()).collect(Collectors.toSet()))
                .deniedIpList(property.getAllowedIps().length == 0 ? new HashSet<>() :
                        Arrays.stream(property.getDeniedIps()).collect(Collectors.toSet()))
                .build();
    }
}
