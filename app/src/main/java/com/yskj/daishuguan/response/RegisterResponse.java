package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: RegisterResponse
 * @Description:
 */

public class RegisterResponse  {

    private String  userid ;
    private String  mobileno ;
    private String  token ;
    private String  invitationcode ;
    private String  realname ;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInvitationcode() {
        return invitationcode;
    }

    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }

    public String getName() {
        return realname;
    }

    public void setName(String name) {
        this.realname = name;
    }
}
