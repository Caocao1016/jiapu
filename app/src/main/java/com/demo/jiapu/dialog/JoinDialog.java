package com.demo.jiapu.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.demo.jiapu.R;

public class JoinDialog extends Dialog {

    private View contentView;

    public JoinDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        contentView = View.inflate(getContext(), R.layout.dialog_join, null);
        setContentView(contentView);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        init();
    }
    private void init() {
        contentView.findViewById(R.id.tv_left).setOnClickListener(v -> dismiss());
        contentView.findViewById(R.id.tv_pay).setOnClickListener(v -> dismiss());

    }

}

