package cn.pxkeji.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName EhcacheUtil
 * @Author MaZhuli
 * @Date 2018/8/28 13:54
 * @Description 本地缓存工具类
 * @Version 1.0
 **/
@Component
public class EhcacheUtil {
    @Autowired
    private CacheManager manager;

    /**
     * @Author MaZhuli
     * @Description 添加缓存
     * @Date 2018/8/29 9:36
     * @Param [cacheName, key, value]
     * @Return void
     **/
    public void put(String cacheName, String key, Object value) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
        cache.flush();//序列化到磁盘
    }
    /**
     * @Author MaZhuli
     * @Description 添加缓存配置过期时间和闲置时间
     * @Date 2018/8/29 9:18
     * @Param [cacheName, key, value, liveTime, idleTime]
     * @Return void
     **/
    public void put(String cacheName, String key, Object value,int liveTime,int idleTime) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        if(liveTime>=0) {
            element.setTimeToLive(liveTime);
        }
        if(idleTime>=0) {
            element.setTimeToIdle(idleTime);
        }
        cache.put(element);
        cache.flush();//序列化到磁盘
    }

    /**
     * @Author MaZhuli
     * @Description 获取缓存
     * @Date 2018/8/28 18:30
     * @Param [cacheName, key]
     * @Return java.lang.Object
     **/
    public Object get(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * @Author MaZhuli
     * @Description 从缓存中移除
     * @Date 2018/8/28 18:31
     * @Param [cacheName, key]
     * @Return void
     **/
    public void remove(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }

}
