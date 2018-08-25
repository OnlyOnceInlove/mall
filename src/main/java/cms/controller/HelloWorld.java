package cms.controller;

import cms.utils.Authorize;
import cms.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @Authorize(setting = "权限-所有权限")
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello() throws Exception {
        System.out.println("jinfu");
        return "123";
    }

    /**
     * @Author MaZhuli
     * @Description
     * @Date 2018/8/25 15:57
     * @Param []
     * @Return java.lang.String
     **/
    @Authorize(setting = "权限-没有权限")
    @RequestMapping(value = "/hell")
    @ResponseBody
    public String hell() throws Exception {
        System.out.println("jinfu");
        return "123";
    }

}
