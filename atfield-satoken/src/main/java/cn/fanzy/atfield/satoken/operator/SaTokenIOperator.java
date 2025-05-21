package cn.fanzy.atfield.satoken.operator;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.atfield.core.model.IOperator;
import cn.fanzy.atfield.satoken.context.StpContext;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnClass(StpContext.class)
public class SaTokenIOperator implements IOperator {

    @Override
    public String getId() {
        String loginId = StpContext.getLoginId();
        if (StrUtil.isNotBlank(loginId)) {
            return loginId;
        }
        try {
            return StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            return "anonymous";
        }
    }

    @Override
    public String getName() {
        String loginName = StpContext.getLoginName();
        if (StrUtil.isNotBlank(loginName)) {
            return loginName;
        }
        try {
            return StpUtil.getSession().getString(SaSession.USER + "_NAME");
        } catch (Exception e) {
            String operatorId = getId();
            if (StrUtil.equals("anonymous", operatorId)) {
                return "匿名用户";
            }
            return operatorId;
        }
    }
}
