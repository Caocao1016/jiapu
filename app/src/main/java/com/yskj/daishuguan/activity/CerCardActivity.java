package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.TextView;

import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerPhoneActivity
 * @Description: 添加银行卡证项
 */

public class CerCardActivity extends BaseActivity {


    @BindView(R.id.tv_name)
    TextView mName ;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_card;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_card_title;
    }

    @Override
    protected void initView() {
        mName.setText("请绑定 "+StringUtil.getValue(RxSPTool.getString(this, Constant.USER_NAME)+" 您名下的银行"));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_next:
                break;
        }
    }
}
