package cn.pxkeji.core;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ApplicationFeatureFilter
 * @Author MaZhuli
 * @Date 2018/8/27 15:25
 * @Description 应用请求参数过滤器
 * @Version 1.0
 **/
public class ApplicationFeatureFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        RequestFeatureContext context = RequestFeatureContext.getInstance(request);
        ApplicationFeature applicationFeature = new ApplicationFeature();
        applicationFeature.setApplicationId("12345");
        context.setFeature(applicationFeature);
        request.setAttribute(RequestFeatureContext.class.getSimpleName(),context);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
