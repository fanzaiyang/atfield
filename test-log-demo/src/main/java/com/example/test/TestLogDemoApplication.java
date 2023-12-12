package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 测试日志演示应用程序
 *
 * @author fanzaiyang
 * @date 2023/12/12
 */
//@EnableWebMvc
@SpringBootApplication
public class TestLogDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestLogDemoApplication.class, args);
    }

}
