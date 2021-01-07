package com.demo.jiapu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.demo.jiapu.R;

public class SortCheckBoxView extends LinearLayout implements View.OnClickListener {
    private String leftTvText;
    private int leftTvColor;
    private String editText;

    private int editTextWidth;
    private boolean checked;

    private TextView textView;
    private NumberAddSubView numberAddSubView;
    private CheckBox checkBox;

    private View view;


    public SortCheckBoxView(Context context) {
        super(context);
    }

    public SortCheckBoxView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_cb_rank, this);
        numberAddSubView = findViewById(R.id.asv_rank_view);


        checkBox = findViewById(R.id.checkbox_rank_view);
        checkBox.setChecked(checked);

        view = findViewById(R.id.ll_rank_view);
        view.setOnClickListener(this);

    }


    public SortCheckBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public int getValue() {
        if (!checked) {
            return numberAddSubView.getValue();
        } else {
            return 0;
        }
    }

    public void setValue(int i) {
        numberAddSubView.setValue(i);
    }
}
