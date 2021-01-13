package com.demo.jiapu.activity;

import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.modle.AutoMateView;
import com.demo.jiapu.presenter.AutoMatePresenter;

import java.util.List;

public class AutoMateActivity extends BaseActivity<AutoMatePresenter> implements AutoMateView {
    @Override
    protected AutoMatePresenter createPresenter() {
        return new AutoMatePresenter(this);
    }

    @Override
    public void onSuccess(int id, List<FamilyBean> response) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
