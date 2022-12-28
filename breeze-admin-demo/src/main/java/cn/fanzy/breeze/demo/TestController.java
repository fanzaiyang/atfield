package cn.fanzy.breeze.demo;

import cn.fanzy.breeze.demo.entity.User;
import cn.fanzy.breeze.web.model.JsonContent;
import okhttp3.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/body")
    public JsonContent<User> bodyTest(@RequestBody User user){
        return JsonContent.success(user);
    }
}
