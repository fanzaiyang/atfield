package cn.fanzy.atfield.tlog.print.callback;

import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;
import org.aspectj.lang.JoinPoint;

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
     * @param param     参数
     * @param joinPoint 加入点
     * @param ignore    忽视
     */
    void before(PrintLogInfo param, JoinPoint joinPoint, boolean ignore);

    /**
     * 执行结束后的回调
     *
     * @param param  对象
     * @param ignore 忽视
     */
    void after(PrintLogInfo param, boolean ignore);
}
