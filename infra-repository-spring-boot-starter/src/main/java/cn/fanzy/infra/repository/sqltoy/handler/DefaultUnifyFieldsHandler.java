package cn.fanzy.infra.repository.sqltoy.handler;

import cn.fanzy.infra.repository.sqltoy.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.model.IgnoreCaseSet;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认统一字段处理程序
 *
 * @author fanzaiyang
 * @date 2023/12/22
 */
@RequiredArgsConstructor
public class DefaultUnifyFieldsHandler implements IUnifyFieldsHandler {

    private final UserDetails userDetails;
    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createBy", userDetails.getUserId());
        map.put("createTime", new Date());
        map.put("updateBy", userDetails.getUserId());
        map.put("updateTime", new Date());
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", userDetails.getUserId());
        map.put("updateTime", new Date());
        return map;
    }

    @Override
    public IgnoreCaseSet forceUpdateFields() {
        IgnoreCaseSet map = new IgnoreCaseSet();
        map.add("updateBy");
        map.add("updateTime");
        return map;
    }
}
