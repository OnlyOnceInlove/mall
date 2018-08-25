package cms.utils;

import cms.utils.Authorize.AuthorizeType;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName AuthorizeVerifier
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 权限校验
 * @Version 1.0
 **/
@Service
public class AuthorizeVerifier {
    /**
     * @Author MaZhuli
     * @Description 权限校验
     * @Date 2018/8/25 16:13
     * @Param [request, authorize]
     * @Return boolean
     **/
    public static boolean has(HttpServletRequest request, Authorize authorize) {
        List<String> privilegeList = Jwt.getUserPrivilege(request);
        String setting = authorize.setting();
        AuthorizeType type = authorize.type();
        String[] arr = setting.split(",");
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            String sett = arr[i];
            if (type == type.ONE) {
                for (String mm : privilegeList) {
                    if (mm.equals(sett)) {
                        return true;
                    } else {
                        continue;
                    }
                }
            } else {
                for (String mm : privilegeList) {
                    if (mm.equals(sett)) {
                        index++;
                        if (index == arr.length) {
                            return true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return false;
    }
}
