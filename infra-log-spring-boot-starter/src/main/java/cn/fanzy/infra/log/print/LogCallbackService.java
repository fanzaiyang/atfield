package cn.fanzy.infra.log.print;

/**
 * 日志回调服务
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
public interface LogCallbackService {

    /**
     * 执行结束后的回调
     *
     * @param bean 对象
     */
    void callback(PrintLogInfo param);

    /**
     * 执行之前的回调
     *
     * @param param 参数
     */
    void before(PrintLogBeforeInfo param);

    /**
     * 打印之前
     *
     * @param param 参数
     */
    void printBefore(PrintLogInfo param);


}
