package cn.pxkeji.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @ClassName RedissonManager
 * @Author MaZhuli
 * @Date 2018/8/29 9:31
 * @Description RedissonManager
 * @Version 1.0
 **/
/*@Configuration*/
public class RedissonManager {
   /* @Value("${spring.redis.clusters}")*/
    private  String cluster;
    /*@Value("${spring.redis.password}")*/
    private String password;

    /*@Bean*/
    public RedissonClient getRedisson() throws IOException {
        String[] nodes = cluster.split(",");
        for(int i=0;i<nodes.length;i++){
            nodes[i] = "redis://"+nodes[i];
        }
        RedissonClient redisson = null;
        Config config = new Config();
        config.useClusterServers() //这是用的集群server
                .setScanInterval(2000) //设置集群状态扫描时间
                .addNodeAddress(nodes);
        redisson = Redisson.create(config);
        String s = redisson.getConfig().toJSON().toString();
        System.out.println(s

        );
        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        return redisson;
    }

}