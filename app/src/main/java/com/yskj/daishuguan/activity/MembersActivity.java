package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.TextView;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: MembersActivity
 * @Description:购买会员卡
 */

public class MembersActivity extends BaseActivity{

    @BindView(R.id.tv_content)
    TextView mContent ;

    @Override
    protected BasePresenter createPresenter() {
        return null;
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

    }

    @Override
    protected void initData() {
        mContent.setText(
                "卡介绍：\n" + "1、专属审批通道，极速下款，审核不通过，3-5个工作日可申请退款。\n" + "2、借款1000元，每日利息低至0.66元。\n" +
                "3、按时履约还款，可获得提额机会。\n" +
                "购买须知：\n" +
                "1、卡每日可售数量有限，售完即止。\n" +
                "2、有效期：自购买之日起7天有效，每张会员卡限使用一次，过期自动" +
                "作废且不可退卡。");
    }


    @OnClick({R.id.tv_sure,R.id.rl_envelope})
    public void onClick (View view){
        switch (view.getId()){
            case  R.id.tv_sure :
                startActivity(MembershipActivity.class);
                break; case  R.id.rl_envelope :
                startActivity(EnvelopeActivity.class);
                break;
                default:
                    break;
        }
    }
}
