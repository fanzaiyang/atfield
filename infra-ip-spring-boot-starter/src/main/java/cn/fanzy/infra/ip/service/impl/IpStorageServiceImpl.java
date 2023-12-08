package cn.fanzy.infra.ip.service.impl;

import cn.fanzy.infra.ip.bean.IpStorageBean;
import cn.fanzy.infra.ip.property.IpProperty;
import cn.fanzy.infra.ip.service.IpStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

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
