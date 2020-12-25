package com.demo.jiapu.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demo.jiapu.R;

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private TextView btn_sub;
    private TextView btn_add;
    private TextView tv_value;

    private int value = 1;//默认值
    private int minValue = 1;//最小值
    private int maxValue = 100;//最大值

    public NumberAddSubView(Context context) {
        super(context);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_number_add_sub, this);
        btn_sub = findViewById(R.id.view_n_a_d_btn_sub);
        btn_add = findViewById(R.id.view_n_a_d_btn_add);
        tv_value = findViewById(R.id.view_n_a_d_tv_value);

        getValue();//获得当前值


        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public int getValue() {
        String valueStr = tv_value.getText().toString().trim();//文本的内容
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    @SuppressLint("SetTextI18n")
    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }


    private void subNumber() {
        if (value > minValue) {
            value -= 1;
        }
        setValue(value);
    }

    /**
     * 加
     */
    private void addNumber() {
        if (value < maxValue) {
            value += 1;
        }
        setValue(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_n_a_d_btn_sub://减
                subNumber();
                if (listener != null) {
                    listener.onButtonSub(v, value);
                }
                break;
            case R.id.view_n_a_d_btn_add://加
                addNumber();
                if (listener != null) {
                    listener.onButtonAdd(v, value);
                }
                break;
        }
    }

    public interface OnNumberClickListener {
        /**
         * 当减少按钮被点击的时候回调
         *
         * @param view
         * @param value
         */
        void onButtonSub(View view, int value);

        /**
         * 当增加按钮被点击的时候回调
         *
         * @param view
         * @param value
         */
        void onButtonAdd(View view, int value);
    }

    public OnNumberClickListener listener;

    /**
     * 设置监听数字按钮
     *
     * @param listener
     */
    public void setOnNumberClickListener(OnNumberClickListener listener) {
        this.listener = listener;
    }
}
