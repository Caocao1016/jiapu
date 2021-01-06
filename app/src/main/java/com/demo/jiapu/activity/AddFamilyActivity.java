package com.demo.jiapu.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.widget.MoreEditView;
import com.demo.jiapu.widget.SwitchView;
import com.hjq.bar.TitleBar;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

public class AddFamilyActivity extends BaseActivity implements SwitchView.onClickCheckedListener {
    private int VIEW_MODE = 1;//1:添加 2:保存

    @BindView(R.id.swv_add_live)
    SwitchView switchView;
    @BindView(R.id.tb_add_title)
    TitleBar mTitleBar;
    @BindView(R.id.ll_add_not_live)
    LinearLayout unLiveLayout;
    @BindView(R.id.ll_add_live)
    LinearLayout liveLayout;

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
//        mTitleBar.getRightView().setVisibility("有子女"?View.GONE:View.VISIBLE);


//        if (逝者){
//            unLiveLayout.setVisibility(View.VISIBLE);
//            liveLayout.setVisibility(View.GONE);
//        }else {
//            unLiveLayout.setVisibility(View.GONE);
//            liveLayout.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void onClick(boolean isChecked) {

    }
}
