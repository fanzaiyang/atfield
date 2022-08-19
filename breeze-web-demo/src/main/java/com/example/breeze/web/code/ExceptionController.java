package com.example.breeze.web.code;

import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/error")
public class ExceptionController {
    @GetMapping("/message")
    public JsonContent<String> errMessage() {
        throw new RuntimeException("这里是自定义异常！");
    }
}
