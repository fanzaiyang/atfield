package cn.fanzy.infra.ip.service;

import jakarta.servlet.http.HttpServletRequest;

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
     * @param request 要求
     */
    void check(HttpServletRequest request);
}
