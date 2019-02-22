package com.yskj.daishuguan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.baselibrary.widget.ClearEditText;
import com.hjq.baselibrary.widget.CountdownView;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.WindowAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.entity.request.SendSmsRequest;
import com.yskj.daishuguan.entity.request.SmsCaptchaRequest;
import com.yskj.daishuguan.modle.CardView;
import com.yskj.daishuguan.presenter.CardPresenter;
import com.yskj.daishuguan.presenter.OCRPresenter;
import com.yskj.daishuguan.response.CardResponse;
import com.yskj.daishuguan.response.CardSmsResponse;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/11
 *
 * @ClassName: CerPhoneActivity
 * @Description: 添加银行卡证项
 */

public class CerCardTwoActivity extends BaseActivity<CardPresenter> implements CardView {

    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_type)
    TextView mType;
    @BindView(R.id.cl_card_number)
    ClearEditText mCardNumber;
    @BindView(R.id.cl_phone)
    ClearEditText mPhone;
    @BindView(R.id.ll_type)
    LinearLayout mLlType;
    @BindView(R.id.cl_number)
    ClearEditText mClNumber;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.cv_register_countdown)
    CountdownView mRcountdown;
    private List<CardResponse.BankResponse> banklist;
    private String code = "";
    private String name = "";
    private String mIDcard = "";
    private String mchntssn;
    private String requestno;

    @Override
    protected CardPresenter createPresenter() {
        return new CardPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cer_card_next;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_card_title;
    }

    @Override
    protected void initView() {
        mPhone.setText(StringUtil.getString(StringUtil.getValue(RxSPTool.getString(this, Constant.USER_MOBILENO))));
    }

    @Override
    protected void initData() {

        OCRRequest ocrRequest = new OCRRequest();
        ocrRequest.token = RxSPTool.getString(this, Constant.TOKEN);
        ocrRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
        mPresenter.postIDCard(ocrRequest);
    }


    @OnClick({R.id.tv_next, R.id.ll_type,R.id.cv_register_countdown,R.id.tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_register_countdown:
                String cardNumber = mCardNumber.getText().toString();
                String type = mType.getText().toString();
                if (StringUtil.isEmpty(type)) {
                    mRcountdown.resetState();
                    UIUtils.showToast("请选择卡类型");
                    return;
                }
                if (StringUtil.isEmpty(cardNumber)) {
                    mRcountdown.resetState();
                    UIUtils.showToast("请输入您的银行卡号");
                    return;
                }
                SendSmsRequest request = new SendSmsRequest();
                request.token = RxSPTool.getString(this, Constant.TOKEN);
                request.userid = RxSPTool.getString(this, Constant.USER_ID);
                request.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
                request.realname = name;
                request.idcardno = mIDcard;
                request.cardno = cardNumber;
                request.bankname = type;
                request.bankcode = code;
                mPresenter.sendMessage(request);
                break;
            case R.id.tv_next:
                String number1 = mClNumber.getText().toString();
                String phone = mPhone.getText().toString();


                if (StringUtil.isEmpty(number1)) {
                    UIUtils.showToast("请输入验证码");
                    return;
                }
//                if (!mCheckbox.isChecked()) {
//                    UIUtils.showToast("请阅读绑卡协议并勾选");
//                    return;
//                }
                rxDialogLoading.show();
                SendSmsRequest sendSmsRequest = new SendSmsRequest();
                sendSmsRequest.token = RxSPTool.getString(this, Constant.TOKEN);
                sendSmsRequest.userid = RxSPTool.getString(this, Constant.USER_ID);
                sendSmsRequest.mobileno = RxSPTool.getString(this, Constant.USER_MOBILENO);
                sendSmsRequest.validatecode = number1;
                sendSmsRequest.requestno = requestno;
                sendSmsRequest.bankCardMobile = phone.indexOf("****") != -1 ?phone :RxSPTool.getString(this, Constant.USER_MOBILENO) ;
                mPresenter.getAuthrealnamerequeset(sendSmsRequest);
                break;
            case R.id.ll_type:
                showPopwindow();
                break;
                case R.id.tv:
                    Intent intent2 = new Intent(CerCardTwoActivity.this, WebViewActivity.class);
                    intent2.putExtra(Constant.WEBVIEW_URL,RxSPTool.getString(this,Constant.REGISTER_PROTOCOL));
                    intent2.putExtra(Constant.WEBVIEW_URL_TITLE,"用户服务协议");
                    startActivity(intent2);
                break;
        }
    }


    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_layout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(mName,
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WindowAdapter windowAdapter = new WindowAdapter(banklist);
        mRecyclerView.setAdapter(windowAdapter);
        TextView mCancel = (TextView) view.findViewById(R.id.tv_cancel);

        windowAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<CardResponse.BankResponse> mList = adapter.getData();
                mType.setText(mList.get(position).getName());
                code = mList.get(position).getCode();
                window.dismiss();
            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });

    }

    @Override
    public void onSuccess(CardResponse response) {

        mName.setText("请绑定 " + StringUtil.getValue(response.getRealname()) + " 您名下的银行");
        banklist = response.getBanklist();
        name = response.getRealname();
        mIDcard = response.getIdcardno();
    }

    @Override
    public void onSmsSuccess(CardSmsResponse response) {
        mchntssn = response .getMchntssn();
        requestno = response .getRequestno();

        UIUtils.showToast("验证码发送成功");

    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
    }

    @Override
    public void onCodeSuccess(BaseResponse response) {

        RxSPTool.putString(this,Constant.CARD_NUMBER,mCardNumber.getText().toString());
        rxDialogLoading.dismiss();
        finish();
    }

    @Override
    public void onError() {

    }


    public class WindowAdapter extends BaseQuickAdapter<CardResponse.BankResponse, BaseViewHolder> {


        public WindowAdapter(List<CardResponse.BankResponse> data) {
            super(R.layout.adapter_window, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CardResponse.BankResponse item) {

            helper.setText(R.id.tv_cancel, item.getName());
            helper.addOnClickListener(R.id.tv_cancel);
        }

    }
}
