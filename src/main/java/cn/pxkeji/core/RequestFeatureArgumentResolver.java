package cn.pxkeji.core;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于方法参数注入
 * 当方法参数是IRequestFeature类型时，从RequestFeatureContext中获取
 */
@Service
public class RequestFeatureArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if(methodParameter.getParameterType() == null){
            return false;
        }else if(methodParameter.getParameterType().equals(RequestFeatureContext.class) || RequestFeature.class .isAssignableFrom(methodParameter.getParameterType())){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (methodParameter.getParameterType() == null) {
            return null;
        } else if (methodParameter.getParameterType().equals(RequestFeatureContext.class)) {
            return request.getAttribute(RequestFeatureContext.class.getSimpleName());
        } else if (RequestFeature.class .isAssignableFrom(methodParameter.getParameterType())) {
            RequestFeatureContext context = (RequestFeatureContext) request.getAttribute(RequestFeatureContext.class.getSimpleName());
            Class<?> parameterType = methodParameter.getParameterType();
            return context.getFeature(parameterType);
        }
        return null;
    }
}