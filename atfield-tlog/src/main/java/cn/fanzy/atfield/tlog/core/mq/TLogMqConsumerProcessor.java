package cn.fanzy.atfield.tlog.core.mq;


import cn.fanzy.atfield.tlog.core.rpc.TLogRPCHandler;

/**
 * tlog的mq消费者处理器
 *
 * @author Bryan.Zhang
 * @since 1.2.0
 */
public class TLogMqConsumerProcessor {

    private static final TLogRPCHandler tLogRPCHandler = new TLogRPCHandler();

    public static void process(TLogMqWrapBean tLogMqWrapBean, TLogMqRunner tLogMqRunner) {
        tLogRPCHandler.processProviderSide(tLogMqWrapBean);
        try {
            tLogMqRunner.mqConsume(tLogMqWrapBean.getT());
        } finally {
            tLogRPCHandler.cleanThreadLocal();
        }
    }
}
