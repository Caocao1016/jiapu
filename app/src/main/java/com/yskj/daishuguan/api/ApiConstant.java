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
//    public static final String BASE_SERVER_URL = "http://192.168.1.28:8181/rent-server/";
    public static final String BASE_SERVER_URL = "http://120.27.224.36:8181/rent-server/";
    public static final String FACE = "auth/livingbody/face";
    public static final String Idcardocr = "auth/livingbody/idcardocr";
    public static final String contact = "auth/contacts/authcontact";//联系人认证
    public static String CREATEREPORT = "auth/commoperator/createreport";//运营商1-开始请求
    public static String CRAWLINGREPORT = "auth/commoperator/crawlingreport";//运营商1-重复请求
    public static String Delay = "extend/checkExtend";//初始展期
    public static String repayTotal = "repayment/bills/total";////订单详情
    public static String PayJudge = "vyp/initiativeRepayment/repayment";////订单详情
    public static String BMGetSMS = "vyp/initiativeRepayment/reSendVerify";//主动还款 短信重发
    public static String ConfirmPay = "vyp/initiativeRepayment/confirmRepayment";//确认还款还款
    public static String DelayToPay = "vyp/initiativeRepayment/initiativeExtend";//提交展期到发送短信

}
