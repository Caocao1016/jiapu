package com.demo.jiapu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseFullScreenDialog extends Dialog{

    private View contentView;

    public BaseFullScreenDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        contentView = View.inflate(getContext(), getLayoutResId(), null);
        setContentView(contentView);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        init();
    }

    public abstract int getLayoutResId();

    public View getContentView() {
        return contentView;
    }

    public abstract void init();

}
