package cn.fanzy.breeze.auth.function;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.router.SaRouteFunction;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
public class BreezeDefaultRouteFunction implements SaParamFunction {
    @Override
    public void run(Object r) {
        if(r instanceof ResourceHttpRequestHandler){
            ResourceHttpRequestHandler handler=(ResourceHttpRequestHandler) r;
        }
        StpUtil.checkLogin();
    }
}
