package cn.fanzy.breeze.sqltoy.plus.session;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@Slf4j
@ConditionalOnClass(StpUtil.class)
public class BreezeDefaultCurrentSessionHandler implements BreezeCurrentSessionHandler {
    @Override
    public String getCurrentLoginId() {
        log.debug("默认实现。。。");
        return DEFAULT_LOGIN_ID;
    }
}
