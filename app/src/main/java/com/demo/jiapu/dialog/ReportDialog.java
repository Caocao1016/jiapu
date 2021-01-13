package com.demo.jiapu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.demo.jiapu.R;

import java.util.Calendar;
import java.util.Locale;

import top.defaults.view.DateTimePickerView;

public class ReportDialog extends Dialog {

    public ReportDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);

        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);

        findViewById(R.id.tvOne).setOnClickListener(v -> {
            reportListener.setOnClickListener(1);
            dismiss();
        });
        findViewById(R.id.tvTwo).setOnClickListener(v -> {
            reportListener.setOnClickListener(2);
            dismiss();
        });
        findViewById(R.id.tvThree).setOnClickListener(v -> {
            reportListener.setOnClickListener(3);
            dismiss();
        });
        findViewById(R.id.tvFour).setOnClickListener(v -> {
            reportListener.setOnClickListener(4);
            dismiss();
        });
        findViewById(R.id.tvFive).setOnClickListener(v -> {
            reportListener.setOnClickListener(5);
            dismiss();
        });

    }

    private OnReportListener reportListener;

    public interface OnReportListener {
        void setOnClickListener(int type);
    }

    public void setOnClickListener(OnReportListener reportListener) {
        this.reportListener = reportListener;
    }
}
