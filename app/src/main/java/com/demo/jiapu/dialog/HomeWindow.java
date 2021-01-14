package com.demo.jiapu.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.jiapu.R;

public class HomeWindow extends PopupWindow {
    public HomeWindow(Context context) {
        this(context, 0);
    }

    public HomeWindow(Context context, int type) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_adapter_bg));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_home,
                null, false);

        if (type == 1) {
            TextView textView = contentView.findViewById(R.id.tvTop);
            textView.setText("取消置顶");
        }
        contentView.findViewById(R.id.tvEdit).setOnClickListener(v -> {

            reportListener.setOnClickListener(1);
            dismiss();
        });
        contentView.findViewById(R.id.tvTop).setOnClickListener(v -> {
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