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
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.baselibrary.widget.CountdownView;
import com.moxie.client.widget.wave.UiUtils;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.util.StringUtil;
import com.yskj.daishuguan.util.UIUtils;

import butterknife.BindView;
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

public class SmsDialog extends AppCompatDialogFragment {

    @BindView(R.id.cv_register_countdown)
    CountdownView mRcountdown;
    @BindView(R.id.et_number)
    EditText et_number;

    Unbinder unbinder;
    TextView mCode;

    public interface OnNoFinshClickLitener {
        void onNoFinshClick(String code, int id);
    }

    private OnNoFinshClickLitener mNoFinshClickLitener;

    public void setOnTypeClickLitener(OnNoFinshClickLitener mNoFinshClickLitener) {
        this.mNoFinshClickLitener = mNoFinshClickLitener;
    }

    public SmsDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sms, container, false);
//        TextView mCode = view.findViewById(R.id.code);
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

    @OnClick({R.id.tv_rig, R.id.tv_left, R.id.cv_register_countdown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rig://空白

                if (StringUtil.isEmpty(et_number.getText().toString())) {
                    UIUtils.showToast("请输入验证码");
                    return;
                }

                mNoFinshClickLitener.onNoFinshClick(et_number.getText().toString(), R.id.tv_rig);
                dismiss();
                break;
            case R.id.tv_left://我的信息
                dismiss();
                break;
            case R.id.cv_register_countdown://我的信息
                mNoFinshClickLitener.onNoFinshClick(null, R.id.cv_register_countdown);
                break;


        }
    }
}
