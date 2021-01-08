package com.demo.jiapu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.jiapu.R;
import com.demo.jiapu.util.StringUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import top.defaults.view.DateTimePickerView;

public class SelectPhotoDialog extends Dialog {

    private DateTimePickerView dateTimePickerView;
    private TextView mTrue;
    private String dateString;

    public SelectPhotoDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_time);

        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);

        dateTimePickerView = findViewById(R.id.datePickerView);
        mTrue = findViewById(R.id.tv_true);
        dateTimePickerView.setSelectedDate(Calendar.getInstance());
        dateTimePickerView.setOnSelectedDateChangedListener(date -> {
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
            dateString = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
        });

        mTrue.setOnClickListener(v -> {
            timeListener.setOnClickListener(dateString);
            dismiss();
        });
    }

    private  OnClickTimeListener timeListener;

    public interface OnClickTimeListener {
        void setOnClickListener(String date);
    }

    public void setOnClickListener(OnClickTimeListener timeListener) {
        this.timeListener = timeListener;
    }
}
