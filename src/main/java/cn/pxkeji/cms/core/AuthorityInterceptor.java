package cn.pxkeji.cms.core;

import cn.pxkeji.utils.Jwt;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthtokenInterceptor
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description token和权限校验拦截器
 * @Version 1.0
 **/
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //token校验通过,校验用户是否有权限
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod hm = (HandlerMethod) handler;
            Class<?> clazz = hm.getBeanType();
            Method m = hm.getMethod();
            if (clazz != null && m != null) {
                boolean isClzAnnotation = clazz.isAnnotationPresent(Authorize.class);
                boolean isMethondAnnotation = m.isAnnotationPresent(Authorize.class);
                Authorize authorize = null;
                if (isMethondAnnotation) {
                    authorize = m.getAnnotation(Authorize.class);
                } else if (isClzAnnotation) {
                    authorize = clazz.getAnnotation(Authorize.class);
                }
                if (authorize == null) {
                    return true;
                } else if (AuthorizeVerifier.has(request, authorize)) {
                    return true;
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("success", false);
                    map.put("msg", "您没有该权限!");
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().println(JSONObject.toJSONString(map));
                    return false;
                }
            }
        }
        return true;
    }
}