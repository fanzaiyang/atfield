package cn.fanzy.atfield.sqltoy.operator;

import cn.fanzy.atfield.core.model.Operator;
import cn.fanzy.atfield.satoken.context.StpContext;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnClass(StpContext.class)
public class SaTokenOperator implements Operator {

    @Override
    public String getId() {
        String loginId = StpContext.getLoginId();
        if (StrUtil.isNotBlank(loginId)) {
            return loginId;
        }
        return "anonymous";
    }

    @Override
    public String getName() {
        String loginName = StpContext.getLoginName();
        if (StrUtil.isNotBlank(loginName)) {
            return loginName;
        }
        String operatorId = getId();
        if (StrUtil.equals("anonymous", operatorId)) {
            return "匿名用户";
        }
        return operatorId;
    }
}
