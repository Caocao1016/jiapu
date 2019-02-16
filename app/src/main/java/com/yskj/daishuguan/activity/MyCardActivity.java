package com.yskj.daishuguan.activity;

import android.widget.TextView;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.util.StringUtil;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/15
 *
 * @ClassName: MyCardActivity
 * @Description: 我的银行卡
 */

public class MyCardActivity extends BaseActivity {

    @BindView(R.id.tv_number_card)
    TextView mNumber;
    @BindView(R.id.tv_card)
    TextView mCard;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_card;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_my_card;
    }

    @Override
    protected void initView() {
        mCard.setText(StringUtil.getValue(getIntent().getStringExtra("name")));
        String card = getIntent().getStringExtra("card");
        if (!StringUtil.isEmpty(card)){
            mNumber.setText(getFileAddSpace(card.substring(card.length()-4)));
        }else {
            mNumber.setText("* * * * ");
        }

    }
    public static String getFileAddSpace(String replace) {
        String regex = "(.{1})";
        replace = replace.replaceAll(regex, "$1 ");
        return replace;
    }

    @Override
    protected void initData() {

    }
}
