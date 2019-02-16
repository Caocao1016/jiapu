package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorizeResponse
 * @Description:
 */

public class AuthorizeResponse {

    private String userId ;
    private String authId ;
    private String authName ;
    private String beAuthName ;
    private String authTime ;
    private String expiryTime ;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getBeAuthName() {
        return beAuthName;
    }

    public void setBeAuthName(String beAuthName) {
        this.beAuthName = beAuthName;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
