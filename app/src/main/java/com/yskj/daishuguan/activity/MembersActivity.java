package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.entity.request.MembersRequest;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.modle.MembersView;
import com.yskj.daishuguan.presenter.MembersPresenter;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: MembersActivity
 * @Description:购买会员卡
 */

public class MembersActivity extends BaseActivity<MembersPresenter> implements MembersView {

    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.tv_number)
    TextView mNumber;
    @BindView(R.id.tv_money)
    TextView mMoney;
    @BindView(R.id.tv_right)
    TextView mRight;
    @BindView(R.id.tv_original_price)
    TextView mPrice;
    private NoFinshDialog finshDialog;
    private String mListID;
    private int anInt;
    private String type;

    @Override
    protected MembersPresenter createPresenter() {
        return new MembersPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_members;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_members_title;
    }

    @Override
    protected void initView() {

        anInt = getIntent().getIntExtra("moneyList", 0);
        type = getIntent().getStringExtra("type");
        String rate = RxSPTool.getString(this, Constant.BEGINNING_RATE);
//        mMoney.setText(StringUtil.getRateMoney(anInt, rate).toString());
        mMoney.setText(RxSPTool.getString(this, Constant.AUDIT_assessValue));
        finshDialog = new NoFinshDialog();

        if (type.equals("repeat")) {
            setTitle("提款服务费");
            mRight.setText("提款服务费");
            mPrice.setText("仅当次有效");
            mContent.setText("提款服务费\n" +
                    "1.保证本次放款成功\n" +
                    "2.本次放款中的信息服务\n" +
                    "3.代收本次放款的支付通道费用\n"
            );
        } else {
            setTitle("授信服务费");
            mRight.setText("授信服务");
            mPrice.setText("有效期：2年");
            mContent.setText("授信服务费\n" +
                    "1.本次授信（有效期：2年）流程中的信息中介\n" +
                    "2.本次授信及本次放款中的信息服务\n" +
                    "3.授信期限内账户安全监管服务（有效期：2年）\n" +
                    "4.授信期限内借款账户状态变更通知（有效期：2年）\n" +
                    "5.代收本次放款的资金支付通道费用（本次）\n");
        }


        finshDialog.setOnTypeClickLitener(new NoFinshDialog.OnNoFinshClickLitener() {
            @Override
            public void onNoFinshClick() {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        OCRRequest request = new OCRRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        mPresenter.couponTotal(request);

    }


    @OnClick({R.id.tv_sure, R.id.rl_envelope, R.id.tv_xieyi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                MembersRequest membersRequest = new MembersRequest();
                membersRequest.userId = RxSPTool.getString(this, Constant.USER_ID);
                membersRequest.couponIds = mListID;
                membersRequest.token = RxSPTool.getString(this, Constant.TOKEN);
                membersRequest.menberPrice = anInt;
                membersRequest.type = type;
                membersRequest.menberRate = RxSPTool.getString(this, Constant.BEGINNING_RATE);
                mPresenter.creditList(membersRequest);
                break;
            case R.id.rl_envelope:
                startActivityForResult(new Intent(MembersActivity.this, EnvelopeActivity.class), 100);
                break;
            case R.id.tv_xieyi:
//                Intent intent2 = new Intent(MembersActivity.this, WebViewActivity.class);
//                intent2.putExtra(Constant.WEBVIEW_URL, RxSPTool.getString(this, Constant.MEMBER_PROTOCOL));
//                intent2.putExtra(Constant.WEBVIEW_URL_TITLE, "会员卡协议");
//                startActivity(intent2);
                break;
            default:
                break;
        }
    }


    @Override
    public void onLeftClick(View v) {
        if (!type.equals("repeat")) {
            finshDialog.show(getSupportFragmentManager(), "set");
        }
    }


    @Override
    public void onSuccess(String response) {
        Intent intent = new Intent(this, MembershipActivity.class);
        intent.putExtra("money", response);
        intent.putExtra("type", getIntent().getStringExtra("type"));
        startActivity(intent);
        finish();
    }

    @Override
    public void onNumberSuccess(ManagementResponse response) {
        mNumber.setText("有" + response.getNotUsed() + "个红包");
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onSmsSuccess(BaseResponse response) {

    }

    @Override
    public void onSendSuccess(BaseResponse response) {

    }

    @Override
    public void onError() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 2) {
            if (requestCode == 100) {
                mListID = data.getStringExtra("mListID");
                //设置结果显示框的显示数值
            }
        }
    }
}
