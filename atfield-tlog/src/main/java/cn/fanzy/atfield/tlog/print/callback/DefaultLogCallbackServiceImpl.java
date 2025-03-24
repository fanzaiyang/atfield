package cn.fanzy.atfield.tlog.print.callback;

import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

/**
 * 默认日志回调服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultLogCallbackServiceImpl implements LogCallbackService {

    @Override
    public void before(PrintLogInfo param, JoinPoint joinPoint, boolean ignore) {
        // todo 自定义打印
    }

    @Override
    public void after(PrintLogInfo param, boolean ignore) {
        // todo 写入到数据库
    }
}
