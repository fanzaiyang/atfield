package cn.fanzy.atfield.tlog.print.callback;


import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;

/**
 * 日志回调服务
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
public interface LogOperatorService {

    /**
     * 获取用户id
     *
     * @param param 参数
     * @return {@link String}
     */
    String getUserId(PrintLogInfo param);

    /**
     * 获取用户名
     *
     * @param param 参数
     * @return {@link String}
     */
    String getUserName(PrintLogInfo param);

}
