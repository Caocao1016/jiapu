package com.yskj.daishuguan.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yskj.daishuguan.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * CaoPengFei
 * 2019/1/14
 *
 * @ClassName: SettingDialog
 * @Description:
 */

public class NoFinshDialog extends AppCompatDialogFragment {


    Unbinder unbinder;


    public interface OnNoFinshClickLitener {
        void onNoFinshClick();
    }

    private OnNoFinshClickLitener mNoFinshClickLitener;

    public void setOnTypeClickLitener(OnNoFinshClickLitener mNoFinshClickLitener) {
        this.mNoFinshClickLitener = mNoFinshClickLitener;
    }

    public NoFinshDialog(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_no_finsh, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x33000000));

        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_rig, R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rig://空白
                mNoFinshClickLitener.onNoFinshClick();
                dismiss();
                break;
            case R.id.tv_left://我的信息
                dismiss();
                break;


        }
    }
}
