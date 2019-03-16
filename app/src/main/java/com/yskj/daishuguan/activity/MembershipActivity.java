package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.MemberSmsRequest;
import com.yskj.daishuguan.entity.evbus.FinshEvenbus;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.modle.MembersView;
import com.yskj.daishuguan.presenter.MembersPresenter;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.util.ProgressDialogUtils;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;
import com.yskj.daishuguan.view.RechargeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/1/10
 *
 * @ClassName: MembershipActivity
 * @Description:
 */

public class MembershipActivity extends BaseActivity<MembersPresenter> implements MembersView {

    @BindView(R.id.tv_number)
    TextView mNumber;
    @BindView(R.id.tv_new_number)
    TextView mNewNumber;
    @BindView(R.id.tv_cz)
    TextView mCZ;
    @BindView(R.id.tv_finsh)
    TextView mFinsh;

    private ImmersionBar mImmersionBar;
    private ProgressDialogUtils mDialog;
    private ImageView img_back;
    private ProgressDialogUtils mDialogUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_ship;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_members_ship_title;
    }

    @Override
    protected void initView() {

        mDialogUtils = new ProgressDialogUtils();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        mNumber.setText(StringUtil.getValue(getIntent().getStringExtra("money")));
        mNewNumber.setText(StringUtil.getValue(getIntent().getStringExtra("money")));

        mCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogLoading.show();
                BannerRequest bannerRequest = new BannerRequest();
                bannerRequest.token = RxSPTool.getString(MembershipActivity.this, Constant.TOKEN);
                bannerRequest.userid = RxSPTool.getString(MembershipActivity.this, Constant.USER_ID);
                bannerRequest.mobileno = RxSPTool.getString(MembershipActivity.this, Constant.USER_MOBILENO);
                bannerRequest.type = getIntent().getStringExtra("type");
                mPresenter.memberPayment(bannerRequest);

            }
        });
        mFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinshEvenbus(FinshEvenbus event) {

        rxDialogLoading.show();
        MemberSmsRequest memberSmsRequest = new MemberSmsRequest();
        memberSmsRequest.paramCode = event.password;
        memberSmsRequest.token = RxSPTool.getString(MembershipActivity.this, Constant.TOKEN);
        memberSmsRequest.userid = RxSPTool.getString(MembershipActivity.this, Constant.USER_ID);
        memberSmsRequest.type = getIntent().getStringExtra("type");
        mPresenter.memberConfirmRepayment(memberSmsRequest);


    }


    @Override
    protected MembersPresenter createPresenter() {
        return new MembersPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onNumberSuccess(ManagementResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
        if (response.getRetcode() == 9503){
            finish();
        }
    }

    @Override
    public void onSmsSuccess(BaseResponse response) {
        rxDialogLoading.dismiss();
        startActivity(MembershipFinshActivity.class);
        finish();

    }

    @Override
    public void onSendSuccess(BaseResponse response) {

        rxDialogLoading.dismiss();
        UIUtils.showToast("验证码发送成功");
        new RechargeDialog(getApplicationContext(), MembershipActivity.this, mCZ, StringUtil.getValue(getIntent().getStringExtra("money"))).showConnectPopup();

    }

    @Override
    public void onError() {
        rxDialogLoading.dismiss();
    }
}
