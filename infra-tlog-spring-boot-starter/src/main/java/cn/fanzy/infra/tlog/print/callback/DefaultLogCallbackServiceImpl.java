package cn.fanzy.infra.tlog.print.callback;

import cn.fanzy.infra.tlog.configuration.property.TLogProperty;
import cn.fanzy.infra.tlog.print.bean.PrintLogInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认日志回调服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultLogCallbackServiceImpl implements LogCallbackService {

    private final TLogProperty property;

    @Override
    public void callback(PrintLogInfo param) {

    }

    @Override
    public void before(PrintLogInfo param) {

    }

    @Override
    public String getUserId(PrintLogInfo param) {
        return null;
    }

    @Override
    public String getUserName(PrintLogInfo param) {
        return null;
    }
}
