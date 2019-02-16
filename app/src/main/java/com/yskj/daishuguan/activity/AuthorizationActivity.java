package com.yskj.daishuguan.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vondear.rxtool.RxSPTool;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.dialog.NoFinshDialog;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.view.SignatureDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: AuthorizationActivity
 * @Description: 信息授权
 */

public class AuthorizationActivity extends BaseActivity {

    @BindView(R.id.iv_signature)
    ImageView mIvSignature;

  @BindView(R.id.tv_card)
  TextView mCard;


    private SignatureDialog signatureDialog;
    private NoFinshDialog finshDialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
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

//        mCard.setText(RxSPTool.getString(this, Constant.BEGINNING_RATE).substring(card.length()-4));

        finshDialog = new NoFinshDialog();
        signatureDialog = new SignatureDialog();

        signatureDialog.setOnTypeClickLitener(new SignatureDialog.OnSignatureClickLitener() {
            @Override
            public void onSignatureClick(Bitmap bitmap) {
                mIvSignature.setImageBitmap(bitmap);
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
        finshDialog.show(getSupportFragmentManager(),"set");
    }

    @OnClick({R.id.rl_card, R.id.rl_agreement, R.id.iv_signature, R.id.rl_more_agreement, R.id.rl_signature, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_signature:
                signatureDialog.show(getSupportFragmentManager(), "set");
                mIvSignature.setImageResource(R.mipmap.ic_qianming);
                break;
            case R.id.rl_card:
                break;
            case R.id.rl_agreement:
                startActivity(AgreementActivity.class);
                break;
            case R.id.rl_more_agreement:
                break;
            case R.id.rl_signature:
                break;
            case R.id.tv_sure:

                startActivity(MembersActivity.class);

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
}
