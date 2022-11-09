package cn.fanzy.breeze.admin.module.system.attachments.vo;

import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinyMCEVo {
    private String location;

    public String getLocation() {
        if (StrUtil.isBlank(location)) {
            return location;
        }
        List<String> stringList = StrUtil.split(location, StringPool.QUESTION_MARK);
        return stringList.size() > 0 ? stringList.get(0) : location;
    }
}
