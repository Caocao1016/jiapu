package com.demo.jiapu.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.CreateHomeActivity;

public class ViewHomeErrorView extends RelativeLayout {


    public ViewHomeErrorView(Context context) {
        this(context, null);
    }

    public ViewHomeErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewHomeErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_home_error, this);
        inView(context);
    }


    private void inView(Context context){

        findViewById(R.id.tv_create).setOnClickListener(v -> context.startActivity(new Intent(getContext(), CreateHomeActivity.class)));
    }
}
