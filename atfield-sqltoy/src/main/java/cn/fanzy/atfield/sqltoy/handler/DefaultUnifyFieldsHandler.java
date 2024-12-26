package cn.fanzy.atfield.sqltoy.handler;

import cn.fanzy.atfield.core.model.Operator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认统一字段处理程序
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultUnifyFieldsHandler implements IUnifyFieldsHandler {
    private final Operator operator;

    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createTime", new Date());
        map.put("updateTime", new Date());
        // 这里兼容了一些老版本的字段命名
        map.put("createdTime", new Date());
        map.put("updatedTime", new Date());
        try {
            map.put("createBy", operator.getId());
            map.put("updateBy", operator.getId());
            // 这里兼容了一些老版本的字段命名
            map.put("createdBy", operator.getId());
            map.put("updatedBy", operator.getId());
        } catch (Exception ignored) {
        }
        map.putIfAbsent("createBy", "anonymous");
        map.putIfAbsent("updateBy", "anonymous");
        // 这里兼容了一些老版本的字段命名
        map.putIfAbsent("createdBy", "anonymous");
        map.putIfAbsent("updatedBy", "anonymous");
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());
        // 这里兼容了一些老版本的字段命名
        map.put("updatedTime", new Date());
        try {
            map.put("updateBy", operator.getId());
            // 这里兼容了一些老版本的字段命名
            map.put("updatedBy", operator.getId());
        } catch (Exception ignored) {
        }
        map.putIfAbsent("updateBy", "anonymous");
        // 这里兼容了一些老版本的字段命名
        map.putIfAbsent("updatedBy", "anonymous");
        return map;
    }

}
