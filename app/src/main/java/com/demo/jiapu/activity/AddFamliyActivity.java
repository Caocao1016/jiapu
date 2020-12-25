package com.demo.jiapu.activity;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;

public class AddFamliyActivity extends BaseActivity {
    private int VIEW_MODE = 1;//1:添加 2:保存

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_family;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_add_title;
    }

    @Override
    protected void initView() {
        switch (VIEW_MODE) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
