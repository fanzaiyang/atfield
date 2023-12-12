package cn.fanzy.infra.ip.util;


import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Set;

/**
 * IP工具类
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public class IpUtil extends Ipv4Util{

    /**
     * 包含
     * <pre>
     * 示例：
     *  > 1.168.1.1                 设置单个IP的白名单
     * 	> 192.*    	                设置ip通配符,对一个ip段进行匹配
     * 	> 192.168.3.17-192.168.3.38	设置一个IP范围
     * 	> 255.168.4.0/24		    设置一个网段
     * 	> *                         所有
     * </pre>
     *
     * @param ip     ip
     * @param ipList ip列表
     * @return boolean
     */
    public static boolean isContains(String ip, Set<String> ipList) {
        // 简单匹配
        if (ipList.isEmpty() || ipList.contains(ip) || ipList.contains("*")) {
            return true;
        }
        for (String itemIp : ipList) {
            // 处理*
            if (StrUtil.equals(itemIp, "*")) {
                return true;
            }
            // 处理单个IP
            if (StrUtil.equals(itemIp, ip)) {
                return true;
            }
            // 处理192.168.0.*,192.168.*.1
            if (itemIp.contains("*")) {
                return Ipv4Util.matches(itemIp, ip);
            }
            // 处理IP范围，类似192.168.0.0-192.168.2.1或192.168.1.1/24
            if (itemIp.contains("-")||itemIp.contains("/")) {
                List<String> rangeIpList = Ipv4Util.list(itemIp, true);
                return rangeIpList.contains(ip);
            }
        }
        return false;
    }
}
