package cn.fanzy.atfield.sqltoy.handler;

import cn.fanzy.atfield.sqltoy.entity.ICurrentUserInfo;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DefaultUnifyFieldsHandler implements IUnifyFieldsHandler {
    private final ICurrentUserInfo currentUserInfo;

    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createBy", currentUserInfo.getUserId());
        map.put("createTime", new Date());
        map.put("updateBy", currentUserInfo.getUserId());
        map.put("updateTime", new Date());
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", currentUserInfo.getUserId());
        map.put("updateTime", new Date());
        return map;
    }

//    @Override
//    public IgnoreCaseSet forceUpdateFields() {
//        IgnoreCaseSet map = new IgnoreCaseSet();
//        map.add("updateBy");
//        map.add("updateTime");
//        return map;
//    }
}
