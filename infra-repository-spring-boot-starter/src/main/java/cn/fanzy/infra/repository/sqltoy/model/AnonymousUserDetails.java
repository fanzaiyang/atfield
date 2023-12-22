package cn.fanzy.infra.repository.sqltoy.model;

public class AnonymousUserDetails implements UserDetails{
    @Override
    public String getUserId() {
        return "anonymous";
    }
}
