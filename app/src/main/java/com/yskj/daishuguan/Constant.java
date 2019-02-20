package com.yskj.daishuguan;

/**
 * CaoPengFei
 * 2018/11/20
 *
 * @ClassName: Constant
 * @Description:
 */

public class Constant {


    //商户所需参数
//    public static String merchantcode = "d5jdIC";
//    public static String appid = "20181019";
//    public static String clienttype = "android";
//    public static String appsecret = "WDQB@2017uy760918LK0K2D";

  public static String merchantcode = "yxTOxX";
    public static String appid = "20181019";
    public static String clienttype = "android";
    public static String appsecret = "WDQB@2017uy760918LK0K2D";


    public static final String CODED_CONTENT = "codedContent";
    public static final String INTENT_ZXING_CONFIG = "zxingConfig";
    public static final  int PAGE_SIZE = 10;

    public static String SP_IS_ONE_SHOW = "isOneShow";
    public static String INVITATION_CODE = "invitationcode";
    public static String TOKEN = "token";
    public static String USER_HEAD = "user_head";
    public static String USER_ID = "user_id";
    public static String USER_MOBILENO = "user_mobileNo";
    public static String USER_NAME = "user_realName";
    public static String USER_BIG_MONEY = "user_big_money";  //最大额度

    public static String APP_UPDALE_TITLE = "更新提示";
    public static String APP_UPDALE_TRUE = "立即更新";
    public static String APP_UPDALE_FALSE = "退出";


    public static String WEBVIEW_URL = "webview_url";

    public static String GPS_LATITUDE = "gps_latitude";
    public static String GPS_LONGITUDE = "gps_longitude";
    public static String GPS_ADDRESS = "gps_address";

    /**
     * 身份证调用
     */
    public static  int SCAN_TYPE_SINGLE_BACK = 1;
    public static  int SCAN_TYPE_SINGLE_FRONT = 0;
    public static  int REQUEST_CODE_SCAN = 2;
    public static  int REQUEST_CODE_RESULT = 1;
    public static final String COMPLEXITY = "comlexity";
    public static final String NOTICE = "notice";
    public static final String DETECTLIST = "detectList";
    public static final String DEFAULTDETECTLIST = "BLINK MOUTH NOD YAW";

    public static final String ACTIVITY_ID_CARE = "id_card";


    public static final String CARD_NUMBER = "card_number";

    /**
     * 认证等
     */
    public static String PRO_EXPLAIN= "proExplain";  ////产品介绍
    public static String LOAN_JUAGE= "loanJudge";  ////可否借款
    public static String CREDIT_JUDGE= "creditJudge";  ////授信
    public static String AUTH_JUDGE= "authJudge";  ////授信
    public static String AUDIT_CREDIT_LIMIT= " auditCreditLimit";  ////授信金额

    /**
     * 通用数据
     */
    public static String CONTACT_WAY = "contactWay";  //平台客服联系方式
    public static String AUTH_VALID_DAY= "authValidDay";  //有效期
    public static String PRICE_RAGGE = "priceRange";  //授信额度区间
    public static String DAY_RATE = "dayRate";  //日利率
    public static String BEGINNING_RATE = "beginningInterestRate";   //费率
    public static String HELP_PAGE_URL = "helpPageUrl";  //帮助页面地址
    public static String CONTACT_TIME = "contactWayTime";  //客服热线工作时间
    public static String ABOUT_US_URL = "aboutUsPageUrl";  //关于我们页面地址
    public static String EGISTER_URL = "egisterProtocolUrl";  //注册协议
    public static String REGISTER_PDF = "registerProtocolPdf";  //注册协议pdf
    public static String REGISTER_HTML = "registerProtocolHtml";  //注册协议html
    public static String AUTHOR_PDF = "authorProtocolPdf";  //授权协议pdf
    public static String AUTHOR_HTML = "authorProtocolHtml";  //授权协议html


    public static String CHARGE_PROTOCOL = "charge_protocol";  //授权协议html
    public static String REGISTER_PROTOCOL = "register_protocol";  //APP注册服务与隐私政策
    public static String DATA_QUERY_PROTOCOL = "data_query_protocol";  //第三方数据查询及报送授权书
    public static String EXTEND_PROTOCOL = "extend_protocol";  //贷款展期合同
    public static String LOAN_PROTOCOL = "loan_protocol";  //贷款合同
    public static String MEMBER_PROTOCOL = "member_protocol";  //会员卡服务协议
    public static String CREDIT_PROTOCOL = "credit_protocol";  //征信查询及报送授权书


    public static String IS_LOGIN = "isLogin";  //用户是否登录。0：未登录；1：已登录


}
