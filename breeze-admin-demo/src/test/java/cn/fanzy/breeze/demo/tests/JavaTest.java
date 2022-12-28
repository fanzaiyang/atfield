package cn.fanzy.breeze.demo.tests;

import cn.fanzy.breeze.demo.entity.User;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

public class JavaTest {
    @Test
    void hello() {
        String param = JSONUtil.toJsonStr(User.builder()
                .code("1001")
                .build());
        HttpUtil.createPost("http://localhost:8080/test/body")
                .header("content-type","application/json;charset=utf-8")
                .body(param)
                .execute();
    }

}
