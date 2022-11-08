package cn.fanzy.breeze.auth.jwt;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.exception.ApiDisabledException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.jwt.SaJwtTemplate;
import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.jwt.exception.SaJwtException;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * 微风jwt为无状态
 *
 * @author fanzaiyang
 * @date 2022-11-08
 */
public class BreezeJwtForStateless extends StpLogic {
    /**
     * Sa-Token 整合 jwt -- Stateless 无状态
     */
    public BreezeJwtForStateless() {
        super(StpUtil.TYPE);
    }

    /**
     * Sa-Token 整合 jwt -- Stateless 无状态
     * @param loginType 账号体系标识
     */
    public BreezeJwtForStateless(String loginType) {
        super(loginType);
    }

    /**
     * 获取jwt秘钥
     * @return /
     */
    public String jwtSecretKey() {
        String keyt = getConfig().getJwtSecretKey();
        SaTokenException.throwByNull(keyt, "请配置jwt秘钥");
        return keyt;
    }

    //
    // ------ 重写方法
    //

    // ------------------- 获取token 相关 -------------------

    /**
     * 创建一个TokenValue
     */
    @Override
    public String createTokenValue(Object loginId, String device, long timeout, Map<String, Object> extraData) {
        return SaJwtUtil.createToken(loginType, loginId, device, timeout, extraData, jwtSecretKey());
    }

    /**
     * 获取当前会话的Token信息
     * @return token信息
     */
    @Override
    public SaTokenInfo getTokenInfo() {
        SaTokenInfo info = new SaTokenInfo();
        info.tokenName = getTokenName();
        info.tokenValue = getTokenValue();
        info.isLogin = isLogin();
        info.loginId = getLoginIdDefaultNull();
        info.loginType = getLoginType();
        info.tokenTimeout = getTokenTimeout();
        info.sessionTimeout = SaTokenDao.NOT_VALUE_EXPIRE;
        info.tokenSessionTimeout = SaTokenDao.NOT_VALUE_EXPIRE;
        info.tokenActivityTimeout = SaTokenDao.NOT_VALUE_EXPIRE;
        info.loginDevice = getLoginDevice();
        return info;
    }

    // ------------------- 登录相关操作 -------------------

    /**
     * 创建指定账号id的登录会话
     * @param id 登录id，建议的类型：（long | int | String）
     * @param loginModel 此次登录的参数Model
     * @return 返回会话令牌
     */
    @Override
    public String createLoginSession(Object id, SaLoginModel loginModel) {
        SaTokenException.throwByNull(id, "账号id不能为空");

        // ------ 1、初始化 loginModel
        loginModel.build(getConfig());

        // ------ 2、生成一个token
        String tokenValue = createTokenValue(id, loginModel.getDeviceOrDefault(), loginModel.getTimeout(), loginModel.getExtraData());

        // $$ 发布事件：账号xxx 登录成功
        SaTokenEventCenter.doLogin(loginType, id, tokenValue, loginModel);

        return tokenValue;
    }

    /**
     * 获取指定Token对应的账号id (不做任何特殊处理)
     */
    @Override
    public String getLoginIdNotHandle(String tokenValue) {
        try {
            JSONObject payloads = SaJwtUtil.getPayloadsNotCheck(tokenValue, loginType, jwtSecretKey());
            return String.valueOf(payloads.get(SaJwtTemplate.LOGIN_ID));
        } catch (SaJwtException e) {
            return null;
        }
    }

    /**
     * 会话注销
     */
    @Override
    public void logout() {
        // 如果连token都没有，那么无需执行任何操作
        String tokenValue = getTokenValue();
        if(SaFoxUtil.isEmpty(tokenValue)) {
            return;
        }

        // 从当前 [storage存储器] 里删除
        SaHolder.getStorage().delete(splicingKeyJustCreatedSave());

        // 如果打开了Cookie模式，则把cookie清除掉
        if(getConfig().getIsReadCookie()){
            SaHolder.getResponse().deleteCookie(getTokenName());
        }
    }

    /**
     * 获取当前 Token 的扩展信息
     */
    @Override
    public Object getExtra(String key) {
        return getExtra(getTokenValue(), key);
    }

    /**
     * 获取指定 Token 的扩展信息
     */
    @Override
    public Object getExtra(String tokenValue, String key) {
        return SaJwtUtil.getPayloads(tokenValue, loginType, jwtSecretKey()).get(key);
    }


    // ------------------- 过期时间相关 -------------------

    /**
     * 获取当前登录者的 token 剩余有效时间 (单位: 秒)
     */
    @Override
    public long getTokenTimeout() {
        return SaJwtUtil.getTimeout(getTokenValue(), loginType, jwtSecretKey());
    }


    // ------------------- id 反查 token 相关操作 -------------------

    /**
     * 返回当前会话的登录设备类型
     * @return 当前令牌的登录设备类型
     */
    @Override
    public String getLoginDevice() {
        // 如果没有token，直接返回 null
        String tokenValue = getTokenValue();
        if(tokenValue == null) {
            return null;
        }
        // 如果还未登录，直接返回 null
        if(!isLogin()) {
            return null;
        }
        // 获取
        return SaJwtUtil.getPayloadsNotCheck(tokenValue, loginType, jwtSecretKey()).getStr(SaJwtUtil.DEVICE);
    }


    // ------------------- Bean对象代理 -------------------

    /**
     * [禁用] 返回持久化对象
     */
    @Override
    public SaTokenDao getSaTokenDao() {
        throw new ApiDisabledException();
    }

}
