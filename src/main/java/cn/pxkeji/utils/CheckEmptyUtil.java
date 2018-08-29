package cn.pxkeji.utils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckEmptyUtil
 * @Author MaZhuli
 * @Date 2018/8/28 14:02
 * @Description 判断对象是否为空或者没有内容
 * @Version 1.0
 **/
public class CheckEmptyUtil {
    public static boolean isEmpty(Map map) {
        if (null == map) {
            return true;
        }
        if (map.entrySet().size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List list) {
        if (null == list) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }
}
