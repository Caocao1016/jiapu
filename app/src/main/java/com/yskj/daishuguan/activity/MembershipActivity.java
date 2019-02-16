package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.entity.evbus.FinshEvenbus;
import com.yskj.daishuguan.util.ProgressDialogUtils;
import com.yskj.daishuguan.view.RechargeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/1/10
 *
 * @ClassName: MembershipActivity
 * @Description:
 */

public class MembershipActivity extends BaseActivity {

    @BindView(R.id.tv_number)
    TextView mNumber ;
    @BindView(R.id.tv_new_number)
    TextView mNewNumber ;
    @BindView(R.id.tv_cz)
    TextView mCZ ;
    @BindView(R.id.tv_finsh)
    TextView mFinsh ;

    private ImmersionBar mImmersionBar;
    private ProgressDialogUtils mDialog;
    private ImageView img_back;
    private ProgressDialogUtils mDialogUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_ship;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_members_ship_title;
    }

    @Override
    protected void initView() {
        mDialogUtils =new ProgressDialogUtils();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        mNumber.setText(RxSPTool.getString(MembershipActivity.this, "moneyNumber"));
        mNewNumber.setText(RxSPTool.getString(MembershipActivity.this, "moneyNumber"));

        mCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RechargeDialog(getApplicationContext(), MembershipActivity.this,mCZ).showConnectPopup();

            }
        });
        mFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinshEvenbus(FinshEvenbus event) {
        mDialogUtils.showDialogYuan(MembershipActivity.this,"请求中。。。");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                mDialogUtils.dismissDialog(MembershipActivity.this);
                startActivity(new Intent(MembershipActivity.this, MembershipFinshActivity.class));
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);//3秒后执行TimeTask的run方法

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
