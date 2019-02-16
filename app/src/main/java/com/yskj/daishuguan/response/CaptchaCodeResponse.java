package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2019/2/13
 *
 * @ClassName: CaptchaCodeResponse
 * @Description:
 */

public class CaptchaCodeResponse {

    private String imgBase64;
    private String token;

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
