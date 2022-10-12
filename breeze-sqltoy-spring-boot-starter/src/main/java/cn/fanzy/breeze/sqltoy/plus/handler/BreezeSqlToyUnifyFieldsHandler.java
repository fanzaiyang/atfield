package cn.fanzy.breeze.sqltoy.plus.handler;

import cn.fanzy.breeze.sqltoy.plus.session.BreezeCurrentSessionHandler;
import cn.hutool.extra.spring.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.IgnoreCaseSet;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class BreezeSqlToyUnifyFieldsHandler implements IUnifyFieldsHandler {

    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createBy", getLoginId());
        map.put("createTime", new Date());
        map.put("updateBy", getLoginId());
        map.put("updateTime", new Date());
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
        return SpringUtil.getBean(BreezeCurrentSessionHandler.class).getCurrentLoginId();
    }
}