package cn.fanzy.breeze.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.entity.SysLog;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.logs.model.AppInfoModel;
import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.model.UserInfoModel;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync
@AllArgsConstructor
public class BreezeAdminLoginCallbackService implements BreezeLogCallbackService {
    private final BreezeAdminProperties properties;

    @Override
    public void callback(BreezeRequestArgs args) {
        if (BreezeAdminProperties.ErrorEnum.none.equals(properties.getErrorLevel())) {
            return;
        }
        SqlToyHelperDao sqlToyHelperDao = SpringUtils.getBean(SqlToyHelperDao.class);
        SysLog sysLog = SysLog.builder()
                .traceId(args.getTraceId())
                .name(args.getBizName())
                .clientIp(args.getClientIp())
                .userId(StpUtil.isLogin() ? StpUtil.getLoginIdAsString() : "UN_LOGIN_USER")
                .userInfo(args.getUserInfo())
                .startTime(args.getStartTime())
                .endTime(args.getEndTime())
                .spendSecond((int) args.getProceedSecond())
                .requestUri(args.getRequestUrl())
                .requestMethod(args.getRequestMethod())
                .requestData(JSONUtil.toJsonStr(args.getRequestData()))
                .responseData(JSONUtil.toJsonStr(args.getResponseData()))
                .success(args.isSuccess() ? 1 : 0)
                .build();
        if (properties.getErrorLevel() == null || StrUtil.equalsIgnoreCase(properties.getErrorLevel().name(), BreezeAdminProperties.ErrorEnum.all.name())) {
            ThreadUtil.execute(() -> sqlToyHelperDao.save(sysLog));
        }
        if (!args.isSuccess() && StrUtil.equalsIgnoreCase(properties.getErrorLevel().name(), BreezeAdminProperties.ErrorEnum.error.name())) {
            ThreadUtil.execute(() -> sqlToyHelperDao.save(sysLog));
        }
    }

    @Override
    public UserInfoModel getUserInfo(String userId) {
        return new UserInfoModel();
    }

    @Override
    public AppInfoModel getAppInfo(String appId) {
        return new AppInfoModel();
    }
}
