package cn.fanzy.atfield.tlog.print.callback;

import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;

/**
 * 日志回调服务
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
public interface LogCallbackService {
    /**
     * 执行之前的回调
     *
     * @param param 参数
     */
    void before(PrintLogInfo param);

    /**
     * 执行结束后的回调
     *
     * @param param 对象
     */
    void after(PrintLogInfo param);
}
