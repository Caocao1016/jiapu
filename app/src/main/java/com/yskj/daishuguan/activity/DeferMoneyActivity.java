package com.yskj.daishuguan.activity;

import android.widget.TextView;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: DeferMoneyActivity
 * @Description: 申请展期
 */

public class DeferMoneyActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView mContent;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acttivity_money_defer;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_defer_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mContent.setText("温馨提示：\n" +
                "1.申请展期后我平台将从您绑定的银行卡扣缴展期费，请确认你的银 行卡余额充足，以免申请展期失败。\n" +
                "2.我平台仅支持在应还款目前申请展期，请合理安排时间。");
    }
}
