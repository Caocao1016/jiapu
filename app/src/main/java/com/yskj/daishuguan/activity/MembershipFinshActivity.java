package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.evbus.FinshMoneyEvenbus;
import com.yskj.daishuguan.entity.request.BannerRequest;
import com.yskj.daishuguan.entity.request.SubmitRequest;
import com.yskj.daishuguan.modle.CommonDataView;
import com.yskj.daishuguan.presenter.CommonDataPresenter;
import com.yskj.daishuguan.response.BannerResponse;
import com.yskj.daishuguan.response.CommonDataResponse;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.util.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
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

public class MembershipFinshActivity extends BaseActivity<CommonDataPresenter>  implements CommonDataView{

    @BindView(R.id.tv_sure)
    TextView mCZ ;
    @BindView(R.id.tv_time)
    TextView mTime ;

    private ImmersionBar mImmersionBar;
    private ProgressDialogUtils mDialog;
//    Timer timer = new Timer();
//    private int recLen = 3;
//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    recLen--;
//                    mTime.setText(recLen+"");
//                    if (recLen ==0){
//                        timer.cancel();
//                        EventBus.getDefault().post(new FinshMoneyEvenbus(1));
//                        finish();
//                    }
//                }
//            });
//        }
//    };


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
//        timer.schedule(task, 1000, 1000);       // timeTask
    }

    @Override
    protected void initData() {
        mCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                timer.cancel();
//                EventBus.getDefault().post(new FinshMoneyEvenbus(1));
//                finish();
                BannerRequest homeInfoRequest = new BannerRequest();
                homeInfoRequest.token = RxSPTool.getString(MembershipFinshActivity.this, Constant.TOKEN);
                homeInfoRequest.userid = RxSPTool.getString(MembershipFinshActivity.this,Constant.USER_ID);
                homeInfoRequest.cycle = RxSPTool.getString(MembershipFinshActivity.this,Constant.AUTH_VALID_DAY);
                mPresenter.homeInfo(homeInfoRequest);
            }
        });
    }

    @Override
    protected CommonDataPresenter createPresenter() {
        return new CommonDataPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
//        task.cancel();
    }



    @Override
    public void onGetCommonDataSuccess(CommonDataResponse response) {

    }

    @Override
    public void onGetBannerSuccess(List<BannerResponse> response) {

    }

    @Override
    public void onHomeInfoSuccess(HomeInfoResponse response) {
        rxDialogLoading.show();
        SubmitRequest submitRequest = new SubmitRequest();
        submitRequest.userId = RxSPTool.getString(this, Constant.USER_ID);
        submitRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        submitRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
        submitRequest.cycle = RxSPTool.getString(this, Constant.AUTH_VALID_DAY);
        submitRequest.loanAmount = response.getAuditCreditLimit();
        submitRequest.productNo = Build.MODEL;
        submitRequest.loanPurpose = "";
        submitRequest.osType = "ANDROID";
        submitRequest.locgps = RxSPTool.getContent(this, Constant.GPS_LATITUDE);
        submitRequest.locaddress = RxSPTool.getContent(this, Constant.GPS_ADDRESS);
        mPresenter.getSubmit(submitRequest);
    }

    @Override
    public void onSubmitSuccess(BaseResponse response) {

        rxDialogLoading.dismiss();
        Intent intent = new Intent(this, CerFinshActivity.class);
        intent.putExtra("what", 2);
        startActivity(intent);
        finish();
    }


    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
    }

    @Override
    public void onError() {
        rxDialogLoading.dismiss();
    }
}
