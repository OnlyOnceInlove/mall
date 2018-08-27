package cn.pxkeji.core;

/**
 * 请求特性过滤器封装
 */
public abstract class RequestFeatureFilter {
    private RequestFeatureContext context;

    public void setFeature(RequestFeature feature) {
        context.setFeature(feature);
    }
}