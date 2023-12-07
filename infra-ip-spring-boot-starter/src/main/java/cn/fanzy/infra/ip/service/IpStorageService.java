package cn.fanzy.infra.ip.service;

import cn.fanzy.infra.ip.bean.IpStorageBean;

/**
 * ip存储服务
 *
 * @author fanzaiyang
 * @date 2023/12/07
 */
public interface IpStorageService {

    /**
     * 获取ip存储
     *
     * @return {@link IpStorageBean}
     */
    IpStorageBean getIpStorage();
}
