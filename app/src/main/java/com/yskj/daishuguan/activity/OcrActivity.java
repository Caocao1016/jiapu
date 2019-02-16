package com.yskj.daishuguan.activity;


import android.view.View;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import butterknife.OnClick;

/**
 * CaoPengFei
 * 2018/11/21
 *
 * @ClassName: OcrActivity
 * @Description: 身份证验证
 */

public class OcrActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ocr;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_ocr_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_ocr_zm, R.id.vi_ocr_fm, R.id.tv_identify})
    public void submit(View view) {

        switch (view.getId()) {
            case R.id.iv_ocr_zm:
                break;
            case R.id.vi_ocr_fm:
                break;

            case R.id.tv_identify:
                break;
            default:
                break;
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
