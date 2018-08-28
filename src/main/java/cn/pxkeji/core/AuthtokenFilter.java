package cn.pxkeji.core;

import cn.pxkeji.cms.core.Authorize;
import cn.pxkeji.cms.core.AuthorizeVerifier;
import cn.pxkeji.utils.Jwt;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthtokenFilter
 * @Author MaZhuli
 * @Date 2018/8/28 10:41
 * @Description 身份及权限过滤器
 * @Version 1.0
 **/
public class AuthtokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().contains("login")) {
            filterChain.doFilter(request, response);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        Jwt.JWTResult jwtResult = Jwt.checkToken(request);
        if (!jwtResult.getStatus()) {
            map.put("msg", jwtResult.getMsg());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(JSONObject.toJSONString(map));
        } else {
            filterChain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {

    }
}
