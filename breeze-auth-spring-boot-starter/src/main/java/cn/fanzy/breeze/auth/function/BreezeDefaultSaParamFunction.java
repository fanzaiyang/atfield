package cn.fanzy.breeze.auth.function;

import cn.dev33.satoken.fun.SaParamFunction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BreezeDefaultSaParamFunction implements SaParamFunction<Object> {
    private final BreezeAuthRouteHandler handler;

    @Override
    public void run(Object r) {
        handler.check(r);
    }
}
