package cn.fanzy.infra.tlog.print.callback;

import cn.fanzy.infra.tlog.print.bean.PrintLogInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultLogUserCallbackServiceImpl implements LogUserCallbackService{
    @Override
    public String getUserId(PrintLogInfo param) {
        return "anonymousUser";
    }

    @Override
    public String getUserName(PrintLogInfo param) {
        return "anonymousUser";
    }
}
