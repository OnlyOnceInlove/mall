package cn.pxkeji.core;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求特性集合
 * 请求特性集合会放到HttpServletRequest.setAttribute中
 */
public class RequestFeatureContext{
    private Map<String,RequestFeature> features;

    public RequestFeatureContext(){
        features=new HashMap<String,RequestFeature>();
    }

    public void setFeature(RequestFeature feature){
        String key = feature.getClass().getSimpleName();
        features.put(key, feature);
    }

    public <TFeature> RequestFeature getFeature(Class<TFeature> feature){
        return features.get(feature.getSimpleName());
    }
    public static RequestFeatureContext getInstance(HttpServletRequest request){
        RequestFeatureContext context = (RequestFeatureContext)request.getAttribute(RequestFeatureContext.class.getSimpleName());
        return context == null ? new RequestFeatureContext() : context;
    }
}