package utils;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    public static final String SECRET = "JKKLJOoasdlfj";
    public static final long calendarField = System.currentTimeMillis();
    public static final long calendarInterval = 1000*60*30;

    public static String createToken(Long userId) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create().withHeader(map)
                .withClaim("iss", "Service")
                .withClaim("aud", "APP")
                .withClaim("userId", null == userId ? null : userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((calendarField + calendarInterval)))
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return null;
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
        Claim user_id_claim = claims.get("userId");
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
