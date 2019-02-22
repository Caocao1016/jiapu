package com.yskj.daishuguan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.entity.request.CreditStartRequest;
import com.yskj.daishuguan.modle.AuthoriztionView;
import com.yskj.daishuguan.presenter.AuthoriztionPresenter;
import com.yskj.daishuguan.response.HomeInfoResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;
import com.yskj.daishuguan.view.SignatureDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: AuthorizationActivity
 * @Description: 信息授权
 */

public class AuthorizationActivity extends BaseActivity<AuthoriztionPresenter> implements AuthoriztionView {

    @BindView(R.id.iv_signature)
    ImageView mIvSignature;

    @BindView(R.id.tv_card)
    TextView mCard;
    @BindView(R.id.tv_money)
    TextView mMoney;
    @BindView(R.id.tv_all_money)
    TextView mAllMoney;
    @BindView(R.id.tv_money_start)
    TextView mStartMoney;

    @BindView(R.id.tv_interest)
    TextView mInterest;
    @BindView(R.id.tv_day)
    TextView mDay;


    private SignatureDialog signatureDialog;
    private NoFinshDialog finshDialog;
    private boolean isSet;
    private Bitmap isSetBitmap;
    private String moeny;

    @Override
    protected AuthoriztionPresenter createPresenter() {
        return new AuthoriztionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authorization;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_card_title;
    }

    @Override
    protected void initView() {
        moeny = getIntent().getStringExtra("MONEY");

        String cardNumber = RxSPTool.getString(this, Constant.CARD_NUMBER);
        String dayRate = RxSPTool.getString(this, Constant.DAY_RATE);
        String ALLdayRate = RxSPTool.getString(this, Constant.AUTH_VALID_DAY);

        if (!StringUtil.isEmpty(cardNumber)) {
            mCard.setText(cardNumber.substring(cardNumber.length() - 4)+"的银行卡");
        }

        mMoney.setText(moeny);
        mStartMoney.setText("应还本金：" + moeny);
        mInterest.setText("应还利息：" + StringUtil.getActualNUmber(Integer.parseInt(moeny), dayRate));
        mDay.setText("周期：" + RxSPTool.getString(this, Constant.AUTH_VALID_DAY));
        mAllMoney.setText("应还总额：" + StringUtil.getALL(Integer.parseInt(moeny), StringUtil.getActualNUmber(Integer.parseInt(moeny), dayRate), ALLdayRate) + "元");

        finshDialog = new NoFinshDialog();
        signatureDialog = new SignatureDialog();

        signatureDialog.setOnTypeClickLitener(new SignatureDialog.OnSignatureClickLitener() {
            @Override
            public void onSignatureClick(Bitmap bitmap) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    isSet = true;
                    isSetBitmap = bitmap;
                    mIvSignature.setImageBitmap(bitmap);
                } else {
                    isSet = false;
                    mIvSignature.setImageResource(R.mipmap.ic_qianming);
                }
                signatureDialog.dismiss();
            }
        });

        finshDialog.setOnTypeClickLitener(new NoFinshDialog.OnNoFinshClickLitener() {
            @Override
            public void onNoFinshClick() {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLeftClick(View v) {
        finshDialog.show(getSupportFragmentManager(), "set");
    }

    @OnClick({R.id.rl_card, R.id.rl_agreement,R.id.rl_money_agreement, R.id.iv_signature, R.id.rl_more_agreement, R.id.rl_signature, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_signature:
                signatureDialog.show(getSupportFragmentManager(), "set");
                mIvSignature.setImageResource(R.mipmap.ic_qianming);
                break;
            case R.id.rl_card:
                break;
            case R.id.rl_agreement:
                Intent intent = new Intent(AuthorizationActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.WEBVIEW_URL,RxSPTool.getString(this,Constant.CREDIT_PROTOCOL));
                intent.putExtra(Constant.WEBVIEW_URL_TITLE,"人行征信授权书");
                startActivity(intent);
                break;
            case R.id.rl_more_agreement:
                Intent intent1 = new Intent(AuthorizationActivity.this, WebViewActivity.class);
                intent1.putExtra(Constant.WEBVIEW_URL,RxSPTool.getString(this,Constant.DATA_QUERY_PROTOCOL));
                intent1.putExtra(Constant.WEBVIEW_URL_TITLE,"三方数据使用授权书");
                startActivity(intent1);
                break;
                case R.id.rl_money_agreement:
                Intent intent2 = new Intent(AuthorizationActivity.this, WebViewActivity.class);
                    intent2.putExtra(Constant.WEBVIEW_URL_TITLE,"借款协议授权书");
                intent2.putExtra(Constant.WEBVIEW_URL,RxSPTool.getString(this,Constant.LOAN_PROTOCOL));
                startActivity(intent2);
                break;
            case R.id.rl_signature:
                signatureDialog.show(getSupportFragmentManager(), "set");
                mIvSignature.setImageResource(R.mipmap.ic_qianming);
                break;
            case R.id.tv_sure:
                if (!isSet) {
                    UIUtils.showToast("请先点击签名");
                    return;
                }
                rxDialogLoading.show();
                CreditStartRequest creditStartRequest = new CreditStartRequest();
                creditStartRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
                creditStartRequest.token = RxSPTool.getString(this, Constant.TOKEN);
                creditStartRequest.locaddress = RxSPTool.getContent(this, Constant.GPS_ADDRESS);
                creditStartRequest.locgps = RxSPTool.getContent(this, Constant.GPS_LATITUDE);
                creditStartRequest.productNo = Build.MODEL;
                creditStartRequest.customerCreditLimit = moeny;
                creditStartRequest.autographPicture = bitmapToBase64(isSetBitmap);
                mPresenter.creditStart(creditStartRequest);
//                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(this);
//                rxDialogSureCancel.getTitleView().setVisibility(View.GONE);
//                rxDialogSureCancel.getSureView().setTextColor(Color.parseColor("#007AFF"));
//                rxDialogSureCancel.getCancelView().setTextColor(Color.parseColor("#007AFF"));
//
//                rxDialogSureCancel.getContentView().setText("借款前需购买会员卡,\n  是否前往购买会员卡？");
//                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        rxDialogSureCancel.cancel();
//                    }
//                });
//                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rxDialogSureCancel.cancel();
//                    }
//                });
//                rxDialogSureCancel.show();
                break;
            default:
                break;
        }
    }
    @SuppressLint("NewApi")
    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;

        try {

            if (bitmap != null) {

                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;

    }


    @Override
    public void onSuccess(BaseResponse response) {
        rxDialogLoading.dismiss();
        Intent intent = new Intent(this, CerFinshActivity.class);
        intent.putExtra("what",1);
        startActivity(intent);
        finish();
//        BannerRequest homeInfoRequest = new BannerRequest();
//        homeInfoRequest.token = RxSPTool.getString(this, Constant.TOKEN);
//        homeInfoRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
//        homeInfoRequest.cycle = RxSPTool.getString(this, Constant.AUTH_VALID_DAY);
//        mPresenter.homeInfo(homeInfoRequest);
    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
        UIUtils.showToast(response.getRetmsg());
        if (response.getRetcode() == 2705){
            Intent intent = new Intent(this, CerFinshActivity.class);
            intent.putExtra("what",1);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onHomeInfoSuccess(HomeInfoResponse response) {
//        rxDialogLoading.dismiss();
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra("moneyList",response.getAuditCreditLimit());
        startActivity(intent);
    }

    @Override
    public void onError() {

    }
}
