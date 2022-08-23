package com.example.breeze.web.exception;

import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/error")
public class ExceptionController {
    @GetMapping("/runtime")
    public JsonContent<String> errMessage() {
        throw new RuntimeException("这里是自定义异常！");
    }

    @SneakyThrows
    @GetMapping("/e")
    public JsonContent<String> err2() {
        throw new Exception("这里是自定义异常！");
    }
}
