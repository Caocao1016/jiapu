package com.yskj.daishuguan.response;

import java.math.BigDecimal;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: CommonDataResponse
 * @Description: 通用数据
 */

public class CommonDataResponse {

    private String servicecall;  //平台客服联系方式
    private String servicecall_time;  //客服热线工作时间
    private String servicecall_desc;
    private String help_page_url;  //帮助页面地址
    private String aboutme_page_url;  //关于我们页面地址
    private String aboutme_page_url_ios;  //关于我们页面地址
    private String register_protocol_ios;
    private String loan_protocol_ios;
    private String author_protocol;  //授权协议html
    private String author_protocol_ios;
    private String loan_strategy;
    private String loan_strategy_ios;
    private String loan_service_protocol;
    private String loan_service_protocol_ios;
    private String author_protocol_pdf;  //授权协议pdf
    private String author_protocol_html;  //注册协议html
    private String reg_protocol_pdf;  //注册协议pdf
    private String reg_protocol_html;
    private String authValidDay;
    private String dayRate;   //日利率
    private String beginningInterestRate;  //利率
    private String priceRange;

    private String charge_protocol;  //委托扣款授权书
    private String register_protocol;   //APP注册服务与隐私政策
    private String data_query_protocol;  //第三方数据查询及报送授权书
    private String extend_protocol;  //贷款展期合同
    private String loan_protocol;  //贷款合同
    private String member_protocol;  //会员卡服务协议
    private String credit_protocol;  //征信查询及报送授权书

    public String getCharge_protocol() {
        return charge_protocol;
    }

    public void setCharge_protocol(String charge_protocol) {
        this.charge_protocol = charge_protocol;
    }

    public String getData_query_protocol() {
        return data_query_protocol;
    }

    public void setData_query_protocol(String data_query_protocol) {
        this.data_query_protocol = data_query_protocol;
    }

    public String getExtend_protocol() {
        return extend_protocol;
    }

    public void setExtend_protocol(String extend_protocol) {
        this.extend_protocol = extend_protocol;
    }

    public String getMember_protocol() {
        return member_protocol;
    }

    public void setMember_protocol(String member_protocol) {
        this.member_protocol = member_protocol;
    }

    private int isLogin;  //用户是否登录。0：未登录；1：已登录

    public String getDayRate() {
        return dayRate;
    }

    public void setDayRate(String dayRate) {
        this.dayRate = dayRate;
    }

    public String getServicecall() {
        return servicecall;
    }

    public void setServicecall(String servicecall) {
        this.servicecall = servicecall;
    }

    public String getServicecall_time() {
        return servicecall_time;
    }

    public void setServicecall_time(String servicecall_time) {
        this.servicecall_time = servicecall_time;
    }

    public String getServicecall_desc() {
        return servicecall_desc;
    }

    public void setServicecall_desc(String servicecall_desc) {
        this.servicecall_desc = servicecall_desc;
    }

    public String getHelp_page_url() {
        return help_page_url;
    }

    public void setHelp_page_url(String help_page_url) {
        this.help_page_url = help_page_url;
    }

    public String getAboutme_page_url() {
        return aboutme_page_url;
    }

    public void setAboutme_page_url(String aboutme_page_url) {
        this.aboutme_page_url = aboutme_page_url;
    }

    public String getAboutme_page_url_ios() {
        return aboutme_page_url_ios;
    }

    public void setAboutme_page_url_ios(String aboutme_page_url_ios) {
        this.aboutme_page_url_ios = aboutme_page_url_ios;
    }

    public String getRegister_protocol() {
        return register_protocol;
    }

    public void setRegister_protocol(String register_protocol) {
        this.register_protocol = register_protocol;
    }

    public String getLoan_protocol() {
        return loan_protocol;
    }

    public void setLoan_protocol(String loan_protocol) {
        this.loan_protocol = loan_protocol;
    }

    public String getRegister_protocol_ios() {
        return register_protocol_ios;
    }

    public void setRegister_protocol_ios(String register_protocol_ios) {
        this.register_protocol_ios = register_protocol_ios;
    }

    public String getLoan_protocol_ios() {
        return loan_protocol_ios;
    }

    public void setLoan_protocol_ios(String loan_protocol_ios) {
        this.loan_protocol_ios = loan_protocol_ios;
    }

    public String getAuthor_protocol() {
        return author_protocol;
    }

    public void setAuthor_protocol(String author_protocol) {
        this.author_protocol = author_protocol;
    }

    public String getCredit_protocol() {
        return credit_protocol;
    }

    public void setCredit_protocol(String credit_protocol) {
        this.credit_protocol = credit_protocol;
    }

    public String getAuthor_protocol_ios() {
        return author_protocol_ios;
    }

    public void setAuthor_protocol_ios(String author_protocol_ios) {
        this.author_protocol_ios = author_protocol_ios;
    }

    public String getLoan_strategy() {
        return loan_strategy;
    }

    public void setLoan_strategy(String loan_strategy) {
        this.loan_strategy = loan_strategy;
    }

    public String getLoan_strategy_ios() {
        return loan_strategy_ios;
    }

    public void setLoan_strategy_ios(String loan_strategy_ios) {
        this.loan_strategy_ios = loan_strategy_ios;
    }

    public String getLoan_service_protocol() {
        return loan_service_protocol;
    }

    public void setLoan_service_protocol(String loan_service_protocol) {
        this.loan_service_protocol = loan_service_protocol;
    }

    public String getLoan_service_protocol_ios() {
        return loan_service_protocol_ios;
    }

    public void setLoan_service_protocol_ios(String loan_service_protocol_ios) {
        this.loan_service_protocol_ios = loan_service_protocol_ios;
    }

    public String getAuthor_protocol_pdf() {
        return author_protocol_pdf;
    }

    public void setAuthor_protocol_pdf(String author_protocol_pdf) {
        this.author_protocol_pdf = author_protocol_pdf;
    }

    public String getAuthor_protocol_html() {
        return author_protocol_html;
    }

    public void setAuthor_protocol_html(String author_protocol_html) {
        this.author_protocol_html = author_protocol_html;
    }

    public String getReg_protocol_pdf() {
        return reg_protocol_pdf;
    }

    public void setReg_protocol_pdf(String reg_protocol_pdf) {
        this.reg_protocol_pdf = reg_protocol_pdf;
    }

    public String getReg_protocol_html() {
        return reg_protocol_html;
    }

    public void setReg_protocol_html(String reg_protocol_html) {
        this.reg_protocol_html = reg_protocol_html;
    }

    public String getAuthValidDay() {
        return authValidDay;
    }

    public void setAuthValidDay(String authValidDay) {
        this.authValidDay = authValidDay;
    }

    public String getBeginningInterestRate() {
        return beginningInterestRate;
    }

    public void setBeginningInterestRate(String beginningInterestRate) {
        this.beginningInterestRate = beginningInterestRate;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}
