package cn.pxkeji.utils;

import cn.pxkeji.cms.core.Constants;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Jwt
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description json web token
 * @Version 1.0
 **/
@Component
public class Jwt {
    //有效时长
    private final static int expiresSecond = 1000 * 60 * 1;

    /**
     * @Author MaZhuli
     * @Description 生成RSA加密token
     * @Date 2018/8/25 16:18
     * @Param [userId, userName, roles, privileges]
     * @Return java.lang.String
     **/
    public static String creatRsaToken(String userId, String userName, String roles, String privileges) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userId", userId)
                .claim("userName", userName)
                .claim("roles", roles)
                .claim("privileges", privileges)
                .signWith(SignatureAlgorithm.RS512, (PrivateKey) RSAUtils.getInsttance().get("private_key"));
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }

    /**
     * @Author MaZhuli
     * @Description 获取
     * @Date 2018/8/25 16:19
     * @Param [jsonWebToken]
     * @Return io.jsonwebtoken.Claims
     **/
    public static Claims checkRsaToken(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey((PublicKey) RSAUtils.getInsttance().get("public_key")).parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @Author MaZhuli
     * @Description 从
     * @Date 2018/8/25 16:19
     * @Param [request]
     * @Return cms.utils.Jwt.JWTResult
     **/
    public static JWTResult checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        PublicKey pbkey = (PublicKey) RSAUtils.getInsttance().get("public_key");
        try {
            Claims claims = Jwts.parser().setSigningKey(pbkey).parseClaimsJws(token).getBody();
            String sub = claims.get("sub", String.class);
            return new JWTResult(true, sub, "合法请求", 200);
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new JWTResult(false, null, "token已过期!", 401);
        } catch (SignatureException e) {
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new JWTResult(false, null, "非法请求!", 401);
        } catch (Exception e) {
            return new JWTResult(false, null, "内部错误!", 500);
        }
    }

    /**
     * @Author MaZhuli
     * @Description 从token获取用户所有信息
     * @Date 2018/8/25 16:58
     * @Param [request]
     * @Return java.util.Map<java.lang.String       ,       java.lang.Object>
     **/
    public static Map<String, Object> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = checkRsaToken(token);
        String userId = claims.get("userId").toString();
        String userName = claims.get("userName").toString();
        List roleList = Tools.JsonTolist(claims.get("roles").toString());
        List privilegeList = Tools.JsonTolist(claims.get("privileges").toString());
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("roleList", roleList);
        map.put("privilegeList", privilegeList);
        return map;
    }

    /**
     * @Author MaZhuli
     * @Description 获取用户Id
     * @Date 2018/8/25 16:59
     * @Param [request]
     * @Return java.lang.String
     **/
    public static String getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = checkRsaToken(token);
        String userId = claims.get("userId").toString();
        return userId;
    }

    /**
     * @Author MaZhuli
     * @Description 获取用户所有权限
     * @Date 2018/8/25 17:00
     * @Param [request]
     * @Return java.util.List<java.lang.String>
     **/
    public static List<String> getUserPrivilege(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = checkRsaToken(token);
        List<String> privilegeList = Tools.JsonTolist(claims.get("privileges").toString());
        return privilegeList;
    }


    public static class JWTResult {
        private boolean status;
        private String uid;
        private String msg;
        private int code;

        public JWTResult() {
            super();
        }

        public JWTResult(boolean status, String uid, String msg, int code) {
            super();
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean getStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
