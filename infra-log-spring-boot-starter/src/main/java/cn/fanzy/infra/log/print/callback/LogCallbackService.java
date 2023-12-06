package cn.fanzy.infra.log.print.callback;

import cn.fanzy.infra.log.print.bean.PrintLogInfo;

/**
 * 日志回调服务
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
public interface LogCallbackService {

    /**
     * 执行结束后的回调
     *
     * @param param 对象
     */
    void callback(PrintLogInfo param);

    /**
     * 执行之前的回调
     *
     * @param param 参数
     */
    void before(PrintLogInfo param);

    /**
     * 获取用户id
     *
     * @param param 参数
     * @return {@link String}
     */
    String getUserId(PrintLogInfo param);

    /**
     * 获取用户名
     *
     * @param param 参数
     * @return {@link String}
     */
    String getUserName(PrintLogInfo param);

}
