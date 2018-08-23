package utils;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj
     */
    public static final String SECRET = "JKKLJOoasdlfj";
    /**
     * token 过期时间: 10天
     */
    public static final long calendarField = System.currentTimeMillis();
    public static final long calendarInterval = 2000;

    public static String createToken(Long userId) throws UnsupportedEncodingException {
        /**
         *  第二部分payload
         *  iss: jwt签发者
            sub: jwt所面向的用户
            aud: 接收jwt的一方
            exp: jwt的过期时间，这个过期时间必须要大于签发时间
            nbf: 定义在什么时间之前，该jwt都是不可用的.
            iat: jwt的签发时间
            jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
         */
        // 第一部分的header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");//声明价目算法为HMAC SHA256算法
        map.put("typ", "JWT");//声明类型为JWT

        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("user_id", null == userId ? null : userId.toString())
                .withIssuedAt(new Date()) // sign time
                .withExpiresAt(new Date((calendarField + calendarInterval))) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        if (null != jwt) {
            return jwt.getClaims();
        }
        return null;
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }

    public static boolean isVaild(String token){
        Map<String, Claim> stringClaimMap = verifyToken(token);
        if (null != stringClaimMap) {
            return true;
        } else {
            return false;
        }
    }

}
