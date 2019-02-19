package com.yskj.daishuguan.api;

/**
 * CaoPengFei
 * 2018/11/27
 *
 * @ClassName: ApiConstant
 * @Description:
 */

public class ApiConstant {
    /**接口根地址*/
    public static final String BASE_SERVER_URL = "http://47.99.151.209:8181/rent-server/";
//    public static final String BASE_SERVER_URL = "http://192.168.1.28:8181/rent-server/";
    public static final String FACE = "auth/livingbody/face";
    public static final String Idcardocr = "auth/livingbody/idcardocr";
    public static final String contact = "auth/contacts/authcontact";//联系人认证
    public static String CREATEREPORT = "auth/commoperator/createreport";//运营商1-开始请求
    public static String CRAWLINGREPORT = "auth/commoperator/crawlingreport";//运营商1-重复请求
    public static String Delay = "extend/checkExtend";//初始展期

}
