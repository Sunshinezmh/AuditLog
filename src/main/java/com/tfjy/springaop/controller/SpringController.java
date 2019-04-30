package com.tfjy.springaop.controller;

import com.tfjy.springaop.annotation.Log;
import com.tfjy.springaop.bean.User;
import com.tfjy.springaop.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;



/**
 * @Class_NAME SpringController
 * @Auther 张明慧
 * @Date 2019/4/25 17:34
 */
@Controller
@RequestMapping("/aop")
@Api(tags = {"审计日志"})
public class SpringController {
    @Resource
    IUserService userService;

    @Log(name="您访问了aop1方法")
    @ResponseBody
    @RequestMapping(value="aop1")
    public String aop1(){
        return "AOP";
    }

    @Log(name="您访问了aop2方法")
    @ResponseBody
    @RequestMapping(value="aop2")
    public String aop2(String string) throws InterruptedException{
        Thread.sleep(1000L);
        User user=new User();
         user.setName(string);
        //userService.save(user);
        return string;
    }

    @Log(name="您访问了aaa方法")
    @PostMapping("/aaa/{id}")
    @ResponseBody
    public Integer aaa(@PathVariable String id,@RequestBody User a) {
        User user=new User();
        System.out.println(a);
        user.setName("abc");
        userService.save(user);
        return 1;
    }


}
