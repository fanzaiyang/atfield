package cn.fanzy.atfield.sqltoy.handler;

import cn.fanzy.atfield.sqltoy.entity.ICurrentUserInfo;
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
    private final ICurrentUserInfo currentUserInfo;

    @Override
    public Map<String, Object> createUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("delFlag", 0);
        map.put("createTime", new Date());
        map.put("updateTime", new Date());
        try {
            map.put("createBy", currentUserInfo.getUserId());
            map.put("updateBy", currentUserInfo.getUserId());
        } catch (Exception e) {
            log.warn("当前用户信息获取失败,默认为：anonymous!");
            map.put("createBy", "anonymous");
            map.put("updateBy", "anonymous");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateUnifyFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());

        try {
            map.put("updateBy", currentUserInfo.getUserId());
        } catch (Exception e) {
            log.warn("当前用户信息获取失败,默认为：anonymous!");
            map.put("updateBy", "anonymous");
        }
        return map;
    }

}
