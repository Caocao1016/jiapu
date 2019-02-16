package com.yskj.daishuguan.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2018/11/21
 *
 * @ClassName: CreditShowActivity
 * @Description: 引导页
 */

public class CreditShowActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.view_alpha)
    View mViewAlpha;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fredit;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.setEnabled(false);
    }

    @Override
    protected void initData() {

//        new ViewpAgerAdapter();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
