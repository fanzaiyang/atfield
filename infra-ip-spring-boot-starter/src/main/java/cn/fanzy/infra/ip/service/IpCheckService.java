package cn.fanzy.infra.ip.service;

import cn.fanzy.infra.ip.bean.IpStorageBean;

/**
 * ip检查服务
 *
 * @author fanzaiyang
 * @date 2023/12/07
 */
public interface IpCheckService {

    /**
     * 检查
     *
     * @param clientIp 客户端IP
     * @param storage  存储
     */
    void check(String clientIp,IpStorageBean storage);
}
