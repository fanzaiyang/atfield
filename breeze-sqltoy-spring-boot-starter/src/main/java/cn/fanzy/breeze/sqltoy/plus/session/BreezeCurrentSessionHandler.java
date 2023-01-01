package cn.fanzy.breeze.sqltoy.plus.session;

/**
 * @author fanzaiyang
 */
public interface BreezeCurrentSessionHandler {
    static final String DEFAULT_LOGIN_ID = "NONE_LOGIN_USER";

    String getCurrentLoginId();
}
