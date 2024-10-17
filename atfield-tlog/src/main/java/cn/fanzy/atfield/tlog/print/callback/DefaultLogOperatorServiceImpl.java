package cn.fanzy.atfield.tlog.print.callback;

import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultLogOperatorServiceImpl implements LogOperatorService {
    @Override
    public String getUserId(PrintLogInfo param) {
        return "anonymousUser";
    }

    @Override
    public String getUserName(PrintLogInfo param) {
        return "anonymousUser";
    }
}
