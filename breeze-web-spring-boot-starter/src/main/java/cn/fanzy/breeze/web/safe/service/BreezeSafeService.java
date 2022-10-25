package cn.fanzy.breeze.web.safe.service;

import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;

public interface BreezeSafeService {
    void count(String loginId);
    void check(String loginId, BreezeSafe annotation);
    void remove(String loginId);
    boolean isShowCode(String loginId);

    String getErrorMsg(String loginId);
}