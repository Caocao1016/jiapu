package com.yskj.daishuguan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vondear.rxtool.RxDeviceTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.CertificationActivity;
import com.yskj.daishuguan.activity.LoginActivity;
import com.yskj.daishuguan.activity.ManagementMoneyActivity;
import com.yskj.daishuguan.activity.MessageActivity;
import com.yskj.daishuguan.activity.MyCardActivity;
import com.yskj.daishuguan.activity.MyShareActivity;
import com.yskj.daishuguan.activity.SettingActivity;
import com.yskj.daishuguan.activity.WebViewActivity;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.entity.evbus.FinshCertificationEvenbus;
import com.yskj.daishuguan.entity.evbus.QuanxianEvenbus;
import com.yskj.daishuguan.entity.evbus.StickyEvenbus;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.UserInfoRequest;
import com.yskj.daishuguan.modle.UserInfoView;
import com.yskj.daishuguan.presenter.UserInfoPresenter;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.response.UserInfoResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/11/20
 *
 * @ClassName: MyFragment
 * @Description:
 */

public class MyFragment extends CommonLazyFragment<UserInfoPresenter> implements UserInfoView {

    @BindView(R.id.ll_name)
    LinearLayout mLlName;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_moeny)
    TextView mTvMoney;
    @BindView(R.id.tv_time)
    TextView mTime;
    @BindView(R.id.tv_certification)
    TextView mTvCertification;
    @BindView(R.id.tv_two_certification)
    TextView mTvtwoCertification;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    private String bankcard;
    private String bankName;

    @Override
    public void setUserVisibleHint(boolean hidden) {
        if (hidden) {
            initview();
            initData();
        }
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_my_title;
    }

    @Override
    protected void initView() {

        initview();
    }

    private void initview() {
        if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("1")) {
            mLlName.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);

            if (RxSPTool.getBoolean(getContext(), Constant.AUTH_JUDGE)) {
                mTvCertification.setText("已认证");
                mTvCertification.setBackgroundColor(Color.parseColor("#F34F03"));
                mTvtwoCertification.setText("已认证");
            } else {
                mTvCertification.setText("未认证");
                mTvCertification.setBackgroundColor(Color.parseColor("#999999"));
                mTvtwoCertification.setText("未认证");
            }
        } else {
            mLlName.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.VISIBLE);
        }

        mTime.setText(RxSPTool.getString(getContext(), Constant.CONTACT_TIME) + " " + RxSPTool.getString(getContext(), Constant.CONTACT_WAY));
    }

    @Override
    public void onRightClick(View v) {
        startActivity(MessageActivity.class);

    }

    @Override
    public void onLeftClick(View v) {
        if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
            UIUtils.showToast("您当前还未登录，请先去登录");
            startActivity(LoginActivity.class);
            return;
        }
        startActivity(SettingActivity.class);
    }

    @Override
    protected void initData() {
        UserInfoRequest request = new UserInfoRequest();
        request.mobileno = RxSPTool.getString(getContext(), Constant.USER_MOBILENO);
        request.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        request.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        mPresenter.getuserInfo(request);


        BannerRequest homeInfoRequest = new BannerRequest();
        homeInfoRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        homeInfoRequest.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        homeInfoRequest.cycle = RxSPTool.getString(getContext(), Constant.AUTH_VALID_DAY);
        mPresenter.homeInfo(homeInfoRequest);

    }


    @OnClick({R.id.tv_money_management, R.id.tv_login, R.id.rl_certification, R.id.rl_card, R.id.rl_help, R.id.tv_help, R.id.tv_call_phone, R.id.tv_share, R.id.tv_card, R.id.tv_my_certification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_money_management:  //红包管理
                if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
                    UIUtils.showToast("您当前还未登录，请先去登录");
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(ManagementMoneyActivity.class);
                break;
            case R.id.tv_call_phone:
                new AlertDialog.Builder(getContext()).setTitle("联系客服")
                        .setMessage(RxSPTool.getString(getContext(), Constant.CONTACT_WAY))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RxDeviceTool.dial(getContext(), RxSPTool.getString(getContext(), Constant.CONTACT_WAY));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                break;
            case R.id.tv_my_certification:  //认证
            case R.id.rl_certification:  //认证
                if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
                    UIUtils.showToast("您当前还未登录，请先去登录");
                    startActivity(LoginActivity.class);
                    return;
                }

                if (mTvCertification.getText().toString().equals("已认证")) {
                    return;
                }
                Intent mIntent = new Intent(getContext(), CertificationActivity.class);
                mIntent.putExtra("maxMoney", RxSPTool.getString(getContext(), Constant.USER_BIG_MONEY));
                startActivity(mIntent);
                break;
            case R.id.tv_login:  //登录
                startActivity(LoginActivity.class);
                break;
            case R.id.tv_more:  //登录
                UIUtils.showToast("该功能正在完善中");
                break;
            case R.id.tv_help:  //登录
            case R.id.rl_help:  //登录
                Intent intent2 = new Intent(getContext(), WebViewActivity.class);
                intent2.putExtra(Constant.WEBVIEW_URL, "http://120.27.224.36:8181/p/help/help.html");
                intent2.putExtra(Constant.WEBVIEW_URL_TITLE, "帮助中心");
                startActivity(intent2);
                break;
            case R.id.tv_share:  //登录
                if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
                    UIUtils.showToast("您当前还未登录，请先去登录");
                    startActivity(LoginActivity.class);
                    return;
                }

                startActivity(MyShareActivity.class);
                break;
            case R.id.tv_card:
            case R.id.rl_card:
                if (RxSPTool.getString(getContext(), Constant.IS_LOGIN).equals("0")) {
                    UIUtils.showToast("您当前还未登录，请先去登录");
                    startActivity(LoginActivity.class);
                    return;
                }
                if (StringUtil.isEmpty(bankName) || StringUtil.isEmpty(bankcard)) {
                    UIUtils.showToast("您当前还未绑定银行卡");
                    return;
                }
                Intent intent = new Intent(getContext(), MyCardActivity.class);
                intent.putExtra("name", bankName);
                intent.putExtra("card", bankcard);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onSuccess(UserInfoResponse response) {
        bankcard = response.getBankcard();
        bankName = response.getBankName();
        mTvMoney.setText("已贷金额：" + StringUtil.getValue(response.getLoanMoney()) + "元");
        mTvName.setText(StringUtil.isEmpty(response.getName()) ? StringUtil.getString(response.getMobile()) : response.getName());
    }

    @Override
    public void onCommonDataSuccess(CommonDataResponse response) {

    }

    @Override
    public void onHomeInfoSuccess(HomeInfoResponse response) {

        //认证
        boolean authJudge = response.isAuthJudge();
        if (authJudge) {
            mTvCertification.setText("已认证");
            mTvCertification.setBackgroundColor(Color.parseColor("#F34F03"));
            mTvtwoCertification.setText("已认证");
        } else {
            mTvCertification.setText("未认证");
            mTvCertification.setBackgroundColor(Color.parseColor("#999999"));
            mTvtwoCertification.setText("未认证");
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

        if (response.getRetcode() == 1508){
            initview();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void QuanxianEvenbus(QuanxianEvenbus event) {
        initview();
        initData();
    }


 /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void StickyEvenbus(StickyEvenbus event) {
        initview();
        initData();
    }



    /**
     * 认证完成后刷新
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinshCertificationEvenbus(FinshCertificationEvenbus event) {
        BannerRequest homeInfoRequest = new BannerRequest();
        homeInfoRequest.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        homeInfoRequest.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        homeInfoRequest.cycle = RxSPTool.getString(getContext(), Constant.AUTH_VALID_DAY);
        mPresenter.homeInfo(homeInfoRequest);
    }
}
