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

    /**
     * 字符串按逗号分隔为List
     *
     * @param photoString
     * @return
     * @author dhr
     */
    public static List handleString(String photoString) {
        List list = new ArrayList();
        String[] strs = photoString.split(",");
        for (String str : strs) {
            list.add(str);
        }
        return list;
    }

    /**
     * 上传单张图片
     *
     * @param files 文件
     * @param path  上传地址
     * @return
     * @author dhr
     */
    public static String saveUploadFile(MultipartFile files, String path) {
        //上传图片
        String fileSavePath = "";
        //上传的图片保存路径
        String filePath = path;
        File pathFile = new File(filePath);// 建文件夹
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        MultipartFile upFile = files;
        if (files.isEmpty()) {
            fileSavePath = "";
        } else {
            String newFileName = UUID.randomUUID().toString() + Tools.getFileExtension(upFile.getOriginalFilename());
            String newFilePath = filePath + newFileName;// 新路径
            File newFile = new File(newFilePath);
            try {
                upFile.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileSavePath = Tools.getFileVirtualPath(newFilePath);//保存到数据库的路径
        }
        return fileSavePath;
    }


    /**
     * 保存文件，以真实文件名称保存
     *
     * @param files
     * @param path
     * @return
     */
    public static String saveTrueUploadFile(MultipartFile files, String path) {
        String fileSavePath = "";
        String filePath = path;
        File pathFile = new File(filePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        MultipartFile upFile = files;
        if (files.isEmpty()) {
            fileSavePath = "";
        } else {
            String newFileName = files.getOriginalFilename();
            String newFilePath = filePath + newFileName;// 新路径
            File newFile = new File(newFilePath);
            try {
                upFile.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //保存到数据库的路径
            fileSavePath = Tools.getFileVirtualPath(newFilePath);
        }
        return fileSavePath;
    }


    /**
     * 上传多张图片
     *
     * @param filess 多个文件数组
     * @param path   上传地址
     * @return
     * @author dhr
     */
    public static String addImages(MultipartFile[] filess, String path) {
        //图片的个数
        //System.out.println("files:"+filess.length);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        //存到数据库的图片集合名称
        String SiteLogos = "";
        for (MultipartFile files : filess) {
            //上传图片
            String SiteLogo = "";
            //上传的图片保存路径
            String filePath = path;
            //建文件夹
            File pathFile = new File(filePath);
            //如果这个文件夹存在!pathFile.exists()会返回false
            if (!pathFile.exists()) {
                //建立目录文件夹
                pathFile.mkdirs();
            }
            MultipartFile upFile = files;
            //判断要上传的文件是否为空，如果为空则返回true
            if (files.isEmpty()) {
                SiteLogo = "";
            } else {
                //获取图片名称
                String newFileName = upFile.getOriginalFilename();
                // 图片保存路径
                String newFilePath = filePath + newFileName;
                File newFile = new File(newFilePath);
                //如果这个图片名字存在！newFile.exists()会返回true
                if (newFile.exists()) {
                    //获取新的图片名
                    newFileName = sdf.format(new Date()) + "_"
                            + upFile.getOriginalFilename();
                    newFilePath = filePath + newFileName;
                    newFile = new File(newFilePath);
                }
                try {
                    upFile.transferTo(newFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //保存到数据库的路径
                SiteLogo = path + newFileName + ",";
                //将所有图片名称按照逗号拼接
                SiteLogos = SiteLogos + SiteLogo;
            }
        }
        return SiteLogos;
    }


    /**
     * 删除图片
     *
     * @param url 配置文件路径
     * @param url 文件路径
     */
    public static void delImg(String url) {
        try {
            String dir = System.getProperty("user.dir");
            if (!StringUtils.isEmpty(url)) {
                String path = dir + url.replace("/", "\\");
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUploadDir() {
        SimpleDateFormat sdfFolderName = new SimpleDateFormat("yyyyMMdd");
        String newFolderName = sdfFolderName.format(new Date());
        String userDir = System.getProperty("user.dir");
        String path = userDir + "\\upload\\" + newFolderName + "\\";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getFileVirtualPath(String path) {
        return path.replace(System.getProperty("user.dir"), "").replaceAll("\\\\", "/");
    }

    public static String getFileExtension(String file) {
        if (com.mysql.jdbc.StringUtils.isNullOrEmpty(file) || file.lastIndexOf(".") == -1) {
            return "";
        }
        return file.substring(file.lastIndexOf("."));
    }

    /**
     * 计算两个日期相差的天，时，分，秒
     *
     * @param endDate
     * @param nowDate
     * @return
     * @author dhr
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }


}
