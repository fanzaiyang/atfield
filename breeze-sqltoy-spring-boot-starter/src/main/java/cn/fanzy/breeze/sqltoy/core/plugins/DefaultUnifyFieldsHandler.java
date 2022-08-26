package cn.fanzy.breeze.sqltoy.core.plugins;

import java.util.HashMap;
import java.util.Map;

public class DefaultUnifyFieldsHandler implements UnifyFieldsHandler {
    @Override
    public Map<String, Object> createUnifyFields() {
        return new HashMap<>();
    }
}
