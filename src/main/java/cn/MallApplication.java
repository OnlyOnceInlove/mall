package cn;

import cn.pxkeji.cms.core.AuthtokenInterceptor;
import cn.pxkeji.core.ApplicationFeatureFilter;
import cn.pxkeji.core.RequestFeatureArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

/**
 * @ClassName MallApplication
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 电商系统启动类
 * @Version 1.0
 **/
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
    /*@Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthtokenInterceptor()).excludePathPatterns("/login/**");
    }*/
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ApplicationFeatureFilter());
        registration.addUrlPatterns("/index/*");
        registration.setName("applicationFeatureFilter");
        return registration;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestFeatureArgumentResolver());
    }
   /* @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new RequestFeatureArgumentResolver());
    }*/
}
