package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.demo.jiapu.R;
import com.demo.jiapu.activity.ReportActivity;

public class TestLeftPopupWindow extends PopupWindow {

    public TestLeftPopupWindow(Context context, int type) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_adapter_bg));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_left_test,
                null, false);

        if (type == 1) {
            contentView.findViewById(R.id.tvAdd).setVisibility(View.GONE);
            contentView.findViewById(R.id.v_line).setVisibility(View.GONE);
        }
        contentView.findViewById(R.id.tvAdd).setOnClickListener(v -> {

            reportListener.setOnClickListener(1);
            dismiss();
        });
        contentView.findViewById(R.id.tv_report).setOnClickListener(v -> {
            reportListener.setOnClickListener(0);
            dismiss();
        });

        setContentView(contentView);
    }


    private OnReportListener reportListener;

    public interface OnReportListener {
        void setOnClickListener(int type);
    }

    public void setOnClickListener(OnReportListener reportListener) {
        this.reportListener = reportListener;
    }
}