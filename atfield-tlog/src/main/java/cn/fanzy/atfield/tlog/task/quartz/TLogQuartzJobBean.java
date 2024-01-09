package cn.fanzy.atfield.tlog.task.quartz;


import cn.fanzy.atfield.tlog.core.rpc.TLogLabelBean;
import cn.fanzy.atfield.tlog.core.rpc.TLogRPCHandler;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class TLogQuartzJobBean extends QuartzJobBean {

    private TLogRPCHandler tLogRPCHandler = new TLogRPCHandler();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            tLogRPCHandler.processProviderSide(new TLogLabelBean());
            executeTask(jobExecutionContext);
        }finally {
            tLogRPCHandler.cleanThreadLocal();
        }
    }

    public abstract void executeTask(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
