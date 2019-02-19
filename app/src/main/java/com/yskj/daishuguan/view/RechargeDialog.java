package com.yskj.daishuguan.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.entity.evbus.FinshEvenbus;

import org.greenrobot.eventbus.EventBus;


/**
 * CaoPengFei
 * 2018/12/5
 *
 * @ClassName: AppVersionDialog
 * @Description:
 */

public class RechargeDialog extends PopupWindow {

    private PopupWindow UpdatePopup;
    private Context mContext;
    private Activity mActivity;
    private View mView;
    private String mMoneyw;

    public RechargeDialog(Context context, Activity activity, View view,String money) {
        super(context);
        mContext = context;
        mActivity = activity;
        mView = view;
        mMoneyw = money;
    }

    public void showConnectPopup() {
        if (UpdatePopup == null) {
            initView();
        }
        if (UpdatePopup.isShowing()) {
            UpdatePopup.dismiss();
        } else {
            UpdatePopup.showAtLocation(mView, Gravity.TOP, 0, 0);
        }
    }

    private void initView() {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_recharge, null);
        UpdatePopup = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        UpdatePopup.setFocusable(true);

        ImageView mFInsh = dialogView.findViewById(R.id.iv_finsh);
        TextView mNUmber = dialogView.findViewById(R.id.tv_new_number);
        mNUmber.setText(mMoneyw);
        mFInsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePopup.dismiss();
            }
        });


      final  PayPsdInputView passwordInputView = (PayPsdInputView) dialogView.findViewById(R.id.password);


        passwordInputView.setComparePassword( new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理
                passwordInputView.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                passwordInputView.setComparePassword("");
                passwordInputView.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                // TODO: 2018/1/3 输完逻辑
                passwordInputView.setComparePassword(inputPsd);
                UpdatePopup.dismiss();
                EventBus.getDefault().post(new FinshEvenbus(inputPsd));
            }
        });
    }

}


