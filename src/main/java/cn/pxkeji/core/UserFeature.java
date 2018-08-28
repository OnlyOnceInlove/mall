package cn.pxkeji.core;

/**
 * @ClassName UserFeature
 * @Author MaZhuli
 * @Date 2018/8/28 8:47
 * @Description 用户信息特性
 * @Version 1.0
 **/
public class UserFeature implements RequestFeature {
    private Integer userId;
    private String userName;
    private String realName;
    private String phoneNo;
    private Integer sex;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserFeature{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", sex=" + sex +
                '}';
    }
}
