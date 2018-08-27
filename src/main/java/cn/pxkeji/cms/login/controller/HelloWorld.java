package cn.pxkeji.cms.login.controller;

import cn.pxkeji.cms.core.Authorize;
import cn.pxkeji.cms.login.pojo.User;
import cn.pxkeji.core.ApplicationFeature;
import cn.pxkeji.core.RequestFeatureContext;
import cn.pxkeji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName HelloWorld
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 测试权限
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/index")
public class HelloWorld {
    @Autowired
    RedisUtil redisUtil;

    /**
     * @Author MaZhuli
     * @Description 权限测试
     * @Date 2018/8/25 15:57
     * @Param []
     * @Return java.lang.String
     **/
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(ApplicationFeature context) throws Exception {
        System.out.println("jinfu");
        System.out.println(context.getApplicationId());
        return "123";
    }

    /**
     * @Author MaZhuli
     * @Description
     * @Date 2018/8/25 15:57
     * @Param []
     * @Return java.lang.String
     **/
    @RequestMapping(value = "/hell")
    @ResponseBody
    public String hell(@Validated @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldError().getDefaultMessage();// 打印出第一个error消息
            List<FieldError> fieldErrors = result.getFieldErrors();// 所有的error
            for (FieldError f : fieldErrors) {
                f.getRejectedValue();
            }
        }
        return "123";
    }
}

