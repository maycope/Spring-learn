package cn.maycope.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */


@RestController
@Api(value = "HelloController",tags = {"测试管理接口"})
public class HelloController {

    @RequestMapping("/hello")
    public  String hello(){
        return  "hello";
    }
}
