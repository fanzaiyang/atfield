package cn.fanzy.atfield.tlog.web.common;

import cn.fanzy.atfield.tlog.common.constant.TLogConstants;
import cn.fanzy.atfield.tlog.core.rpc.TLogLabelBean;
import cn.fanzy.atfield.tlog.core.rpc.TLogRPCHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TLog web这块的逻辑封装类
 *
 * @author Bryan.Zhang
 * @since 1.1.5
 */
public class TLogWebCommon extends TLogRPCHandler {

    private final static Logger log = LoggerFactory.getLogger(TLogWebCommon.class);

    private static volatile TLogWebCommon tLogWebCommon;

    public static TLogWebCommon loadInstance() {
        if (tLogWebCommon == null) {
            synchronized (TLogWebCommon.class) {
                if (tLogWebCommon == null) {
                    tLogWebCommon = new TLogWebCommon();
                }
            }
        }
        return tLogWebCommon;
    }

    public void preHandle(HttpServletRequest request) {
        String traceId = request.getHeader(TLogConstants.TLOG_TRACE_KEY);
        String spanId = request.getHeader(TLogConstants.TLOG_SPANID_KEY);
        String preIvkApp = request.getHeader(TLogConstants.PRE_IVK_APP_KEY);
        String preIvkHost = request.getHeader(TLogConstants.PRE_IVK_APP_HOST);
        String preIp = request.getHeader(TLogConstants.PRE_IP_KEY);

        TLogLabelBean labelBean = new TLogLabelBean(preIvkApp, preIvkHost, preIp, traceId, spanId);

        processProviderSide(labelBean);
    }

    public void afterCompletion() {
        cleanThreadLocal();
    }
}
