package com.example.breeze.web.code;

import cn.fanzy.breeze.web.code.annotation.BreezeCodeChecker;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.model.BreezeImageCode;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/code")
public class CodeController {
    private final BreezeCodeProcessor processor;
    @GetMapping("/image/create")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response,String clientId) {
        BreezeCode code = processor.createAndSend(new ServletWebRequest(request,response), clientId,BreezeCodeType.IMAGE);
        log.info("生成的code：{}",code.getCode());
    }
    @GetMapping("/image/get")
    public JsonContent<String> imageCode(HttpServletRequest request,String clientId) {
        BreezeImageCode code = (BreezeImageCode) processor.create(new ServletWebRequest(request),clientId, BreezeCodeType.IMAGE);
        log.info(code.getCode());
        return JsonContent.success(code.getImageBase64());
    }
    @BreezeCodeChecker(value = BreezeCodeType.IMAGE)
    @GetMapping("/image/valid")
    public JsonContent<String> imageValid(HttpServletRequest request,String clientId,String code) {
        processor.validate(new ServletWebRequest(request,null),BreezeCodeType.IMAGE);
        return JsonContent.success();
    }
}
