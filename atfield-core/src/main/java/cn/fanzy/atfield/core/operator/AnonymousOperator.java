package cn.fanzy.atfield.core.operator;

import cn.fanzy.atfield.core.model.Operator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AnonymousOperator implements Operator {
    @Override
    public String getId() {
        return "anonymous";
    }

    @Override
    public String getName() {
        return "匿名用户";
    }
}
