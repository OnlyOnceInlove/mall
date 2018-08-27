package cn.pxkeji.cms.login.controller;

import cn.pxkeji.cms.core.AuthorizeSettingLoader;
import cn.pxkeji.utils.Jwt;
import cn.pxkeji.utils.Tools;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 用户登录相关Controller
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    SqlSession sqlSession;

    /**
     * @Author MaZhuli
     * @Description 用户登录
     * @Date 2018/8/25 15:54
     * @Param [login, request]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping(value = "/userLogin", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getUserInfo(@RequestBody Map<String, Object> login, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, List<String>> listMap = AuthorizeSettingLoader.get();
        System.out.println(listMap);
        Map<String, Object> result = new HashMap<String, Object>();
        String name = (String) login.get("userName");
        //TODO:是否加密
        String pwd = (String) login.get("userPwd");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd)) {
            result.put("success", false);
            result.put("msg", "用户名或密码不得为空！");
            return result;
        } else {
            String md5pwd = Tools.md5(pwd);
            login.put("userPwd", md5pwd);
            Map m = sqlSession.selectOne("loginDao.login", login);
            if (m != null) {
                String userId = m.get("userId").toString();
                //用户角色
                List<Map<String, Object>> roleList = sqlSession.selectList("roleDao.selectRoleIdByUserId", userId);
                String roles = JSONArray.toJSONString(roleList);
                //用户权限
                List<String> privilegeList = sqlSession.selectList("roleDao.selAuthorityByUserId", userId);
                String privileges = JSONArray.toJSONString(privilegeList);
                //生成token
                String token = Jwt.creatRsaToken(m.get("userId").toString(), m.get("userName").toString(), roles, privileges);

                //TODO:暂时不注销,以便测试
                System.out.println(token);

                result.put("success", true);
                result.put("msg", "登录成功!");
                result.put("data", token);
                return result;
            } else {
                result.put("success", false);
                result.put("msg", "用户名或密码错误！");
                return result;
            }
        }
    }
}
