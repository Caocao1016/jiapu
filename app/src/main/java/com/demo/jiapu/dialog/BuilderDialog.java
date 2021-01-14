package com.demo.jiapu.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.demo.jiapu.R;
import com.demo.jiapu.bean.BuilderBean;

public class BuilderDialog extends BaseFullScreenDialog {
    private BuilderBean builderBean;

    public BuilderDialog(Context context, BuilderBean builderBean) {
        super(context);
        this.builderBean = builderBean;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_builder;
    }

    @Override
    public void init() {
        getContentView().setOnClickListener(v -> dismiss());
        if (null != builderBean) {
            ((TextView) getContentView().findViewById(R.id.tv_builder_name)).setText(builderBean.getNickname());
            ((TextView) getContentView().findViewById(R.id.tv_builder_num)).setText(builderBean.getYp_num());
        }
    }


}
