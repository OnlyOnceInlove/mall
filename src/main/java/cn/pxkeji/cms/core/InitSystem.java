package cn.pxkeji.cms.core;

import cn.pxkeji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName InitSecretKey
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 系统启动后加载数据
 * @Version 1.0
 **/
@Component
public class InitSystem implements ApplicationRunner {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //TODO：首页加载
        //TODO：redis加载缓存
    }
}
