package cn.fanzy.breeze.auth.function;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.router.SaRouteFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BreezeDefaultRouteFunction implements SaRouteFunction {
    @Override
    public void run(SaRequest request, SaResponse response, Object handler) {
        log.info("默认路由实现，可自定义实现！");
    }
}
