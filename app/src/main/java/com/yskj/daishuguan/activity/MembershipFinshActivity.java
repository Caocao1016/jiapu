package com.yskj.daishuguan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.util.ProgressDialogUtils;

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

public class MembershipFinshActivity extends BaseActivity {

    @BindView(R.id.tv_sure)
    TextView mCZ ;
    @BindView(R.id.tv_time)
    TextView mTime ;

    private ImmersionBar mImmersionBar;
    private ProgressDialogUtils mDialog;
    Timer timer = new Timer();
    private int recLen = 3;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    mTime.setText(recLen+"");
                    if (recLen ==0){
                        timer.cancel();
                        RxSPTool.putString(MembershipFinshActivity.this,"isSHow","11111");
                        finish();
                    }
                }
            });
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_ship_fish;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_fish_title;
    }

    @Override
    protected void initView() {
        timer.schedule(task, 1000, 1000);       // timeTask
    }

    @Override
    protected void initData() {
        mCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                RxSPTool.putString(MembershipFinshActivity.this,"isSHow","111");
                finish();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        task.cancel();
    }
}
