package cn.fanzy.atfield.tlog.print.callback;


import cn.fanzy.atfield.tlog.print.bean.LogRecordInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultLogRecordServiceImpl implements LogRecordService {
    @Override
    public void write(LogRecordInfo record) {
        log.info("{} - {} - {} - {}", record.getOperatorName(), record.getOperateType(), record.getContent(), record.getOperateTime());
    }
}
