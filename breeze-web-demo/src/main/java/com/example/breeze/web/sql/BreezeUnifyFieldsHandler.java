package com.example.breeze.web.sql;

import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class BreezeUnifyFieldsHandler implements IUnifyFieldsHandler {
    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("createBy", "0");
        map.put("createTime", new Date());
        map.put("updateBy", "0");
        map.put("updateTime", new Date());
        map.put("delFlag", "0");
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateBy", "0");
        map.put("updateTime", new Date());
        return map;
    }
}
