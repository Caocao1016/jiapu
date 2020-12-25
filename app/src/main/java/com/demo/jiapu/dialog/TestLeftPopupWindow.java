package com.demo.jiapu.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.jiapu.R;
import com.demo.jiapu.activity.ReportActivity;

/**
 * CaoPengFei
 * 2019/6/21
 *
 * @ClassName:
 * @Description:
 */
public class TestLeftPopupWindow extends PopupWindow {

    private PopupWindow UpdatePopup;
    private Activity mContext;
    private View mView;

    public TestLeftPopupWindow(Activity context, View view) {
        super(context);
        mContext = context;
        mView = view;
    }

    public void showConnectPopup() {
        if (UpdatePopup == null) {
            initView();
        }
        if (UpdatePopup.isShowing()) {
            UpdatePopup.dismiss();
        } else {
            UpdatePopup.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        }
    }

    private void initView() {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.popup_left_test, null);
        UpdatePopup = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvAdd = dialogView.findViewById(R.id.tvAdd);
        TextView tv_report = dialogView.findViewById(R.id.tv_report);
        tvAdd.setOnClickListener(v -> {

            new JoinDialog(mContext).show();
            UpdatePopup.dismiss();
        });
        tv_report.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ReportActivity.class));
            UpdatePopup.dismiss();
        });

    }

}