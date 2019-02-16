package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.moxie.client.widget.wave.UiUtils;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.entity.request.MembersRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.MembersView;
import com.yskj.daishuguan.presenter.MembersPresenter;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: MembersActivity
 * @Description:购买会员卡
 */

public class MembersActivity extends BaseActivity<MembersPresenter> implements MembersView {

    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.tv_number)
    TextView mNumber;
    private NoFinshDialog finshDialog;

    @Override
    protected MembersPresenter createPresenter() {
        return new MembersPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_members;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_members_title;
    }

    @Override
    protected void initView() {
        finshDialog = new NoFinshDialog();
        mContent.setText(
                "卡介绍：\n" + "1、专属审批通道，极速下款，审核不通过，3-5个工作日可申请退款。\n" + "2、借款1000元，每日利息低至0.66元。\n" +
                        "3、按时履约还款，可获得提额机会。\n" +
                        "购买须知：\n" +
                        "1、卡每日可售数量有限，售完即止。\n" +
                        "2、有效期：自购买之日起7天有效，每张会员卡限使用一次，过期自动" +
                        "作废且不可退卡。");

    }

    @Override
    protected void initData() {
        OCRRequest request = new OCRRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        mPresenter.couponTotal(request);

    }


    @OnClick({R.id.tv_sure, R.id.rl_envelope})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                MembersRequest membersRequest = new MembersRequest();
                membersRequest.userId = RxSPTool.getString(this, Constant.USER_ID);
                break;
            case R.id.rl_envelope:
                startActivityForResult(new Intent(MembersActivity.this,EnvelopeActivity.class),100);
                break;
            default:
                break;
        }
    }


    @Override
    public void onLeftClick(View v) {
        finshDialog.show(getSupportFragmentManager(), "set");
    }

    @Override
    public void onSuccess(BaseResponse response) {
        startActivity(MembershipActivity.class);
        finish();
    }

    @Override
    public void onNumberSuccess(ManagementResponse response) {
        mNumber.setText("有"+response.getNotUsed()+"个红包");
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onError() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        data.getStringExtra("")
    }
}
