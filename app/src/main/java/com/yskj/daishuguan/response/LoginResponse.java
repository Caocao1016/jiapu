package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataResponse
 * @Description: 用户登陆
 */

public class LoginResponse {

    private String realname;  //用户真实姓名
    private String userimg;  //头像图片地址
    private String mobileno;  //用户手机号
    private String userid;  //用户ID
    private boolean issetpwd;
    private String invitationcode;  //当前用户的邀请码
    private String token;  //用户登录token

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isIssetpwd() {
        return issetpwd;
    }

    public void setIssetpwd(boolean issetpwd) {
        this.issetpwd = issetpwd;
    }

    public String getInvitationcode() {
        return invitationcode;
    }

    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
