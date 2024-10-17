package cn.fanzy.atfield.tlog.print.callback;

import cn.fanzy.atfield.tlog.print.bean.LogRecordInfo;

/**
 * 日志回调服务
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
public interface LogRecordService {
    /**
     * 执行结束后的回调
     *
     * @param record 对象
     */
    void write(LogRecordInfo record);
}
