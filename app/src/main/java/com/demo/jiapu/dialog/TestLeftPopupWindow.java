package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

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

    public TestLeftPopupWindow(Context context) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_adapter_bg));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_left_test,
                null, false);

        contentView.findViewById(R.id.tvAdd).setOnClickListener(v -> {
            new JoinDialog(context).show();
            dismiss();
        });
         contentView.findViewById(R.id.tv_report).setOnClickListener(v -> {
             context.startActivity(new Intent(context, ReportActivity.class));
             dismiss();
         });

        setContentView(contentView);
    }
}