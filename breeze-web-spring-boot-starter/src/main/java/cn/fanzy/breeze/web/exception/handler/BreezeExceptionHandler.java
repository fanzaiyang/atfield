package cn.fanzy.breeze.web.exception.handler;

/**
 * 异常处理程序
 * @author fanzaiyang
 */
public interface BreezeExceptionHandler {

    /**
     * 在发生异常后执行
     *
     * @param e e
     */
    void after(Exception e);
}
