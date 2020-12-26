package com.demo.jiapu.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.widget.SwitchView;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

public class AddFamilyActivity extends BaseActivity implements SwitchView.onClickCheckedListener {
    private int VIEW_MODE = 1;//1:添加 2:保存

    @BindView(R.id.swv_add_live)
    SwitchView switchView;
    @BindView(R.id.ll_add_live)
    LinearLayout liveLayout;
    @BindView(R.id.ll_add_not_live)
    LinearLayout unLiveLayout;

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
       switchView.setOnClickCheckedListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(boolean isChecked) {
        if (!isChecked){
            liveLayout.setVisibility(View.VISIBLE);
            unLiveLayout.setVisibility(View.GONE);
        }else if(isChecked){
            liveLayout.setVisibility(View.GONE);
            unLiveLayout.setVisibility(View.VISIBLE);
        }
    }
}
