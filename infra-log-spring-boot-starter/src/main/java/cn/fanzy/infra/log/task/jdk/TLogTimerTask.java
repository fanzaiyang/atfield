package cn.fanzy.infra.log.task.jdk;



import cn.fanzy.infra.log.core.rpc.TLogLabelBean;
import cn.fanzy.infra.log.core.rpc.TLogRPCHandler;

import java.util.TimerTask;

/**
 * TLog用于jdk TimerTask的替换类
 * @author Bryan.Zhang
 * @since 1.3.0
 */
public abstract class TLogTimerTask extends TimerTask {

    private final TLogRPCHandler tLogRPCHandler = new TLogRPCHandler();

    public void run(){
        try{
            tLogRPCHandler.processProviderSide(new TLogLabelBean());
            runTask();
        }finally {
            tLogRPCHandler.cleanThreadLocal();
        }
    }

    public abstract void runTask();

}
