package com.demo.jiapu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demo.jiapu.R;

public class MoreEditView extends LinearLayout implements View.OnClickListener {

    private String leftTvText;
    private int leftTvColor;
    private String editText;

    private int editTextWidth;
    private boolean checked;

    private TextView textView;
    private EditText editTextView;
    private CheckBox checkBox;

    private View view;

    public MoreEditView(Context context) {
        super(context);
    }

    public MoreEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(attrs);
        initView(context);
    }

    public MoreEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(@Nullable AttributeSet attrs) {
        // 获取自定义属性
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MoreEditView);


        leftTvText = a.getString(R.styleable.MoreEditView_leftTvText);
        leftTvColor = a.getColor(R.styleable.MoreEditView_leftTvColor, Color.rgb(0, 0, 0));
        editText = a.getString(R.styleable.MoreEditView_editText);
        editTextWidth = (int) a.getDimension(R.styleable.MoreEditView_editTextWidth, dp2px(150));
        Log.i("TAG", "init: " + editTextWidth);
        checked = a.getBoolean(R.styleable.MoreEditView_checkSetChecked, false);//false left , true right
        a.recycle();
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_tv_ev_cb, this);

        textView = findViewById(R.id.tv_more_view);
        textView.setText(leftTvText);
        textView.setTextSize(16);
        textView.setTextColor(leftTvColor);

        editTextView = findViewById(R.id.et_more_view);
        editTextView.setHint(editText);
        editTextView.setTextSize(12);
        editTextView.setWidth(editTextWidth);

        checkBox = findViewById(R.id.checkbox_more_view);
        checkBox.setChecked(checked);

        view = findViewById(R.id.ll_more_view);
        view.setOnClickListener(this);


    }

    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        setChecked(!checked);
    }

    private void setChecked(boolean checked) {
        if (checked == this.checked) {
            return;
        }
        this.checked = checked;
        checkBox.setChecked(this.checked);
    }

    public String getValue() {
        if (!checked) {
            return editTextView.getText().toString();
        } else {
            return null;
        }
    }

}
