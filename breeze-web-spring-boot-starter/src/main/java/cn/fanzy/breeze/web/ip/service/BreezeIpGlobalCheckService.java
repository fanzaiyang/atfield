package cn.fanzy.breeze.web.ip.service;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * ip全局检查服务
 *
 * @author fanzaiyang
 * @version 2022-08-16
 */
public interface BreezeIpGlobalCheckService {
    void handler(ServletWebRequest servletWebRequest);
}
