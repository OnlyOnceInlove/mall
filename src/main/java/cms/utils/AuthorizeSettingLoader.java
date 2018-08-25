package cms.utils;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AuthorizeSettingLoader
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 加载所有权限
 * @Version 1.0
 **/
public class AuthorizeSettingLoader {

    private static Map<String, List<String>> map;

    /**
     * @Author MaZhuli
     * @Description 获取所有权限
     * @Date 2018/8/25 16:11
     * @Param []
     * @Return java.util.Map<java.lang.String   ,   java.util.List   <   java.lang.String>>
     **/
    public static Map<String, List<String>> get() {
        if (map == null) {
            map = new HashMap<String, List<String>>();
            ApplicationContext context = SpringContextUtils.getContext();
            Map<String, Object> controllers = context.getBeansWithAnnotation(Controller.class);
            Class<? extends Object> clazz = null;
            for (Map.Entry<String, Object> entry : controllers.entrySet()) {
                clazz = entry.getValue().getClass();

                if (Advised.class.isAssignableFrom(clazz)) {
                    Advised advised = (Advised) context.getBean(clazz);
                    SingletonTargetSource singTarget = (SingletonTargetSource) advised.getTargetSource();
                    clazz = singTarget.getTarget().getClass();
                }

                Authorize authorize = clazz.getAnnotation(Authorize.class);
                setValues(authorize);
                Method[] methods = clazz.getDeclaredMethods();
                for (Method m : methods) {
                    Authorize mAuthorize = m.getAnnotation(Authorize.class);
                    setValues(mAuthorize);
                }
            }
        }
        return map;
    }

    private static void setValues(Authorize authorize) {
        if (authorize == null) {
            return;
        }
        String setting = authorize.setting();
        String[] arr = setting.split(",");
        for (int i = 0; i < arr.length; i++) {
            String[] resourceAction = arr[i].split("-");
            if (!map.containsKey(resourceAction[0])) {
                map.put(resourceAction[0], new ArrayList<String>());
            }

            if (map.get(resourceAction[0]).contains(resourceAction[1])) {
                return;
            }
            map.get(resourceAction[0]).add(resourceAction[1]);
        }
    }
}
