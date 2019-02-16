package com.yskj.daishuguan.activity;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: PaymentDetails
 * @Description: 还款详情
 */

public class PaymentDetailsActivity extends BaseActivity{
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acttivity_payment_details;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_payment_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
