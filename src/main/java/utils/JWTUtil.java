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
    private static final String SECRET = Constants.SECRET_KEY;
    private static final long calendarField = System.currentTimeMillis();
    private static final long calendarInterval = 1000 * 60 * 30;

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

    public static Long getUserId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userId = claims.get("userId");
        if (null == userId || StringUtils.isEmpty(userId.asString())) {
            return null;
        }
        return Long.parseLong(userId.asString());
    }

    public static boolean isVaild(String token) {
        Map<String, Claim> stringClaimMap = verifyToken(token);
        if (null != stringClaimMap) {
            return true;
        } else {
            return false;
        }
    }

}
