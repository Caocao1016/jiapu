package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.TextView;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.entity.evbus.FinshMoneyEvenbus;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerFinshActivity
 * @Description: 提交申请
 */

public class CerFinshActivity extends BaseActivity {

    @BindView(R.id.tv_number)
    TextView mNumber;
    @BindView(R.id.tv_next)
    TextView mNext;


    private int what;   //0 认证  1 授信  2  借款

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_finsh;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_card_title;
    }

    @Override
    protected void initView() {

        what = getIntent().getIntExtra("what", 0);
    }

    @Override
    protected void initData() {

        if (what == 0) {
            mNext.setText("立即查看");
            mNumber.setText("您的认证申请已提交在线审核，预计1小时完成（ 如遇节假日则安排至下一个负工作日审核");
        } else if (what == 1) {
            mNext.setText("立即前往");
            mNumber.setText("您的授信申请已提交在线审核，预计1小时完成（ 如遇节假日则安排至下一个负工作日审核");
        } else if (what == 2) {
            mNext.setText("立即前往");
            mNumber.setText("您的放款申请已提交在线审核，预计1小时完成（ 如遇节假日则安排至下一个负工作日审核");
        }
    }

    @OnClick(R.id.tv_next)
    public void onClick(View view) {

        if (what == 0) {

        } else if (what == 1) {
            EventBus.getDefault().post(new FinshMoneyEvenbus(1));

        } else if (what == 2) {
            EventBus.getDefault().post(new FinshMoneyEvenbus(1));

        }
        finish();
    }
}
