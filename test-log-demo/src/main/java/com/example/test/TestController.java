package com.example.test;

import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import cn.hutool.core.util.RandomUtil;
import com.example.test.vo.DateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 测试控制器
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    @ResponseWrapper
    @GetMapping("/1")
    public String test1() {
        log.info("test1");
        return "test1";
    }
    @ResponseWrapper
    @GetMapping("/2")
    public DateVo test2() {
       return DateVo.builder()
               .id(RandomUtil.randomLong())
               .name(RandomUtil.randomString(10))
               .localDateTime(LocalDateTime.now())
               .today(new Date())
               .now(new Date())
               .localDate(LocalDate.now())
               .localTime(LocalTime.now())
               .build();
    }
    @ResponseWrapper
    @GetMapping("/3")
    public DateVo test3() {
        throw new RuntimeException("自定义异常！");
    }
}
