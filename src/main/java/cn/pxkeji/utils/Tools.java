package cn.pxkeji.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Tools {
    /**
     * json转Map
     *
     * @param json
     * @return
     * @author dhr
     */
    public static Map<String, Object> JsonToMap(String json) {
        Map<String, Object> map = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(json, HashMap.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * json转List
     *
     * @param json
     * @return
     * @author dhr
     */
    public static List JsonTolist(String json) {
        List list = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            list = new ObjectMapper().readValue(json, ArrayList.class);
        } catch (IOException e) {
            // TODO:Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将msg采用MD5算法处理,返回一个String结果
     *
     * @param msg 明文
     * @return 密文
     */
    public static String md5(String msg) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            //原始信息input
            byte[] input = msg.getBytes();
            //加密信息output
            byte[] output = md.digest(input);//加密处理
            //采用Base64将加密内容output转成String字符串
            String s = Base64.encodeBase64String(output);
            return s;
        } catch (Exception ex) {
            System.out.println("md5加密失败");
            return null;
        }
    }

}
