package cn.fanzy.atfield.core.operator;

import cn.fanzy.atfield.core.model.IOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AnonymousIOperator implements IOperator {
    @Override
    public String getId() {
        return "anonymous";
    }

    @Override
    public String getName() {
        return "匿名用户";
    }
}
