package com.yskj.daishuguan.view;

import android.graphics.Bitmap;
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

public class SignatureDialog extends AppCompatDialogFragment {


    Unbinder unbinder;
    private SignView signView;


    public interface OnSignatureClickLitener {
        void onSignatureClick(Bitmap bitmap);
    }

    private OnSignatureClickLitener mOnTypeClickLitener;

    public void setOnTypeClickLitener(OnSignatureClickLitener mOnTypeClickLitener) {
        this.mOnTypeClickLitener = mOnTypeClickLitener;
    }

    public SignatureDialog(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_signature, container, false);
        unbinder = ButterKnife.bind(this, view);
        signView = view.findViewById(R.id.sign_view);
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

    @OnClick({R.id.tv_again, R.id.tv_sure, R.id.mCl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCl://空白
                dismiss();
                break;

                case R.id.tv_again://空白
                signView.clear();
                break;
            case R.id.tv_sure://我的信息
                Bitmap bitmap = signView.getBitmap();
                mOnTypeClickLitener.onSignatureClick(bitmap);
                break;
        }
    }
}
