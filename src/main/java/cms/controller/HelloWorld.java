package cms.controller;

import cms.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/index")
public class HelloWorld {
    @Autowired
    HelloService helloService;
    @RequestMapping(value = "/hello")
    public void sout(){
        List<Map> map = helloService.selVersion();
        System.out.println(map);
    }

}
