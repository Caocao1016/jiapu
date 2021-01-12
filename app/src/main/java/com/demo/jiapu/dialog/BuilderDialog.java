package com.demo.jiapu.dialog;

import android.content.Context;
import android.view.View;

import com.demo.jiapu.R;

public class BuilderDialog extends BaseFullScreenDialog {
    public BuilderDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_builder;
    }

    @Override
    public void init() {
        getContentView().setOnClickListener(v -> dismiss());
    }
}
