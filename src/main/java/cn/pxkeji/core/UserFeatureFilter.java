package cn.pxkeji.core;

import cn.pxkeji.utils.JDBCHelper;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @ClassName UserFeatureFilter
 * @Author MaZhuli
 * @Date 2018/8/28 8:52
 * @Description 用户信息过滤器
 * @Version 1.0
 **/
public class UserFeatureFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        RequestFeatureContext context = RequestFeatureContext.getInstance(request);
        UserFeature userFeature = new UserFeature();
        String userId = request.getParameter("userId").toString();
        JDBCHelper jdbcHelper = new JDBCHelper("select * from userInfo where userId = " + userId);
        ResultSet resultSet = null;
        try {
            resultSet = jdbcHelper.pst.executeQuery();//执行语句，得到结果集
            while (resultSet.next()) {
                String phoneNo = resultSet.getString(4);
                String realName = resultSet.getString(6);
                String sex = resultSet.getString(9);
                String userName = resultSet.getString(2);
                userFeature.setUserId(Integer.parseInt(userId));
                userFeature.setPhoneNo(phoneNo);
                userFeature.setRealName(realName);
                userFeature.setSex(Integer.parseInt(sex));
                userFeature.setUserName(userName);
            }
            resultSet.close();
            jdbcHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.setFeature(userFeature);
        request.setAttribute(RequestFeatureContext.class.getSimpleName(), context);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
