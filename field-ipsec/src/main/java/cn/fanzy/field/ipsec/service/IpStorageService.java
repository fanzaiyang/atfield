package cn.fanzy.field.ipsec.service;

import cn.fanzy.field.ipsec.bean.IpStorageBean;

/**
 * ip存储服务
 * <pre>
 * 示例：
 *  > 1.168.1.1                 设置单个IP的白名单
 * 	> 192.*    	                设置ip通配符,对一个ip段进行匹配
 * 	> 192.168.3.17-192.168.3.38	设置一个IP范围
 * 	> 255.168.4.0/24		    设置一个网段
 * </pre>
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
