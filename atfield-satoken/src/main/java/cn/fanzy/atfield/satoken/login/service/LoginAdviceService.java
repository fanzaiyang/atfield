package cn.fanzy.atfield.satoken.login.service;

import cn.fanzy.atfield.satoken.login.model.LoginAopInfoDto;

/**
 * 登录AOP服务
 *
 * @author fanzaiyang
 * @date 2024/01/16
 */
public interface LoginAdviceService {
    /**
     * 登录之前验证
     *
     * @param dto {@link  LoginAopInfoDto}
     */
    void check(LoginAopInfoDto dto);

    /**
     * 登录失败，记录失败次数
     *
     * @param loginKey  登录密钥
     * @param loginName 登录名
     * @param clientIp  客户端 IP
     * @param property  财产
     */
    void error(LoginAopInfoDto dto);

    /**
     * 成功,删除已记录失败的次数
     *
     * @param loginKey  登录密钥
     * @param loginName 登录名
     * @param clientIp  客户端 IP
     * @param count     计数
     * @param property  财产
     */
    void success(LoginAopInfoDto dto);
}
