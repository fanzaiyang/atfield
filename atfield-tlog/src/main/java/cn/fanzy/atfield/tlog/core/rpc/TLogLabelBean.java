package cn.fanzy.atfield.tlog.core.rpc;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * TLog的日志标签包装类
 *
 * @author Bryan.Zhang
 * @since 1.2.0
 */
@Getter
@Setter
public class TLogLabelBean implements Serializable {

    @Serial
    private static final long serialVersionUID = -4695285303891753977L;
    private String preIvkApp;

    private String preIvkHost;

    private String preIp;

    private String traceId;

    private String spanId;

    private Map<String, Object> extData;

    public TLogLabelBean() {
    }

    public TLogLabelBean(String preIvkApp, String preIvkHost, String preIp, String traceId, String spanId) {
        this.preIvkApp = preIvkApp;
        this.preIvkHost = preIvkHost;
        this.preIp = preIp;
        this.traceId = traceId;
        this.spanId = spanId;
    }

    public void putExtData(String key, Object value) {
        if (ObjectUtil.isNull(extData)) {
            extData = new HashMap<>();
        }
        extData.put(key, value);
    }

}
