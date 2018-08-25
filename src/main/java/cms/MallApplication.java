package cms;

import cms.utils.AuthtokenInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthtokenInterceptor()).excludePathPatterns("/login/**");
    }
}
