package com.demo.jiapu.dialog;


import android.content.Context;

import com.demo.jiapu.R;

public class LoadingDialog extends BaseFullScreenDialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void init() {

    }
}
