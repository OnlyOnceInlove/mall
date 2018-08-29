package cn;

import cn.pxkeji.cms.core.AuthorityInterceptor;
import cn.pxkeji.core.ApplicationFeatureFilter;
import cn.pxkeji.core.AuthtokenFilter;
import cn.pxkeji.core.RequestFeatureArgumentResolver;
import cn.pxkeji.core.UserFeatureFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @ClassName MallApplication
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 电商系统启动类
 * @Version 1.0
 **/

@EnableCaching
@Configuration
@SpringBootApplication
public class MallApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

    /**
     * @Author MaZhuli
     * @Description 配置拦截器
     * @Date 2018/8/25 15:58
     * @Param [registry]
     * @Return void
     **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/*");
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthtokenFilter());
        registration.setFilter(new ApplicationFeatureFilter());
        registration.setFilter(new UserFeatureFilter());
        registration.addUrlPatterns("/*");
        registration.setName("filterChain");
        return registration;
    }
    /**
     * @Author MaZhuli
     * @Description 配置参数解析器
     * @Date 2018/8/29 14:09
     * @Param [argumentResolvers]
     * @Return void
     **/
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestFeatureArgumentResolver());
    }
}
