package cn.fanzy.infra.log.print;

import cn.fanzy.infra.log.configuration.property.TLogProperty;
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
public class DefaultLogCallbackServiceImpl implements LogCallbackService{

    private final TLogProperty property;

    @Override
    public void callback(PrintLogInfo param) {

    }

    @Override
    public void before(PrintLogBeforeInfo param) {

    }
}
