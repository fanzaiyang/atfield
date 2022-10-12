package cn.fanzy.breeze.admin.config.handler;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.IgnoreCaseSet;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微风统一字段处理程序
 *
 * @author fanzaiyang
 * @date 2022-10-12
 */
@Slf4j
public class BreezeSqlToyUnifyFieldsHandler implements IUnifyFieldsHandler {
    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createBy", getLoginId());
        map.put("createTime", new Date());
        map.put("updateBy", getLoginId());
        map.put("updateTime", new Date());
        map.put("revision", 0);
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", getLoginId());
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

    private String getLoginId() {
        return StpUtil.isLogin() ? StpUtil.getLoginIdAsString() : "NONE";
    }
}
