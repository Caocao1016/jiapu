package com.demo.jiapu.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.jiapu.R;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.util.StringUtil;

public class MenuDialog extends BaseFullScreenDialog {

    TextView textView;
    ImageView imageView;
    LinearLayout mLlMenuOpen;
    LinearLayout mLlMenuHome;
    LinearLayout mLlMenuBuilder;
    LinearLayout mLlMenuEdit;
    LinearLayout mLlMenuAdd;

    private FamilyBean familyBean;

    private static final int AVATAR_MALE = R.drawable.ic_avatar_male;//男性默认头像
    private static final int AVATAR_FEMALE = R.drawable.ic_avatar_female;//女性默认头像

    private static final String SEX_FEMALE = "2";//2为女性


    public MenuDialog(Context context, FamilyBean familyBean) {
        super(context);
        this.familyBean = familyBean;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.menu_main_family;
    }

    @Override
    public void init() {
        textView = findViewById(R.id.tv_menu_name);
        if (StringUtil.isEmpty(familyBean.getNickname()))
            textView.setText(familyBean.getSurname() + familyBean.getNames());
        else textView.setText(familyBean.getNickname());
        imageView = findViewById(R.id.iv_menu_avatar);
        RequestOptions requestOptions = new RequestOptions()
                .circleCrop()
                .placeholder(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE)
                .error(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE).centerCrop()
                .dontAnimate();
        Glide.with(getContext())
                .load(familyBean.getMemberImg())
                .apply(requestOptions)
                .into(imageView);

        findViewById(R.id.rl_all).setOnClickListener(v -> dismiss());

        mLlMenuOpen = findViewById(R.id.ll_menu_open);
        mLlMenuOpen.setOnClickListener(v -> {
            reportListener.setOnClickListener(v.getId(), familyBean);
            dismiss();
        });
        mLlMenuHome = findViewById(R.id.ll_menu_home);
        mLlMenuHome.setOnClickListener(v -> {
            reportListener.setOnClickListener(v.getId(), familyBean);
            dismiss();
        });
        mLlMenuBuilder = findViewById(R.id.ll_menu_builder);
        mLlMenuBuilder.setOnClickListener(v -> {
            reportListener.setOnClickListener(v.getId(), familyBean);
            dismiss();
        });
        mLlMenuEdit = findViewById(R.id.ll_menu_edit);
        mLlMenuEdit.setOnClickListener(v -> {
            reportListener.setOnClickListener(v.getId(), familyBean);
            dismiss();
        });
        mLlMenuAdd = findViewById(R.id.ll_menu_add);
        mLlMenuAdd.setOnClickListener(v -> {
            reportListener.setOnClickListener(v.getId(), familyBean);
            dismiss();
        });


    }


    public void setType(boolean home, boolean open, boolean add, boolean edit, boolean builder) {
        mLlMenuHome.setVisibility(home ? View.VISIBLE : View.GONE);
        mLlMenuOpen.setVisibility(open ? View.VISIBLE : View.GONE);
        mLlMenuAdd.setVisibility(add ? View.VISIBLE : View.GONE);
        mLlMenuEdit.setVisibility(edit ? View.VISIBLE : View.GONE);

        if (builder) {
            RelativeLayout.LayoutParams builderParams = (RelativeLayout.LayoutParams) mLlMenuBuilder.getLayoutParams();
            RelativeLayout.LayoutParams homeParams = (RelativeLayout.LayoutParams) mLlMenuHome.getLayoutParams();
            builderParams.removeRule(RelativeLayout.BELOW);
            builderParams.removeRule(RelativeLayout.START_OF);
            builderParams.removeRule(RelativeLayout.ABOVE);
            homeParams.removeRule(RelativeLayout.ABOVE);
            homeParams.removeRule(RelativeLayout.BELOW);

            if (mLlMenuOpen.getVisibility() == View.VISIBLE && mLlMenuHome.getVisibility() == View.VISIBLE) {
                builderParams.addRule(RelativeLayout.START_OF, R.id.ll_menu_item);
                homeParams.addRule(RelativeLayout.BELOW, R.id.ll_menu_item);
            } else if (mLlMenuHome.getVisibility() == View.VISIBLE && mLlMenuOpen.getVisibility() == View.GONE) {
                homeParams.addRule(RelativeLayout.ABOVE, R.id.ll_menu_item);
                builderParams.addRule(RelativeLayout.BELOW, R.id.ll_menu_item);
            } else if (mLlMenuHome.getVisibility() == View.GONE && mLlMenuOpen.getVisibility() == View.GONE) {
                builderParams.addRule(RelativeLayout.ABOVE, R.id.ll_menu_item);
            } else {
                builderParams.addRule(RelativeLayout.BELOW, R.id.ll_menu_item);
            }
            mLlMenuBuilder.setLayoutParams(builderParams);
            mLlMenuHome.setLayoutParams(homeParams);
        }

        mLlMenuBuilder.setVisibility(builder ? View.VISIBLE : View.GONE);


    }

    private OnReportListener reportListener;

    public interface OnReportListener {
        void setOnClickListener(int type, FamilyBean familyBean);
    }

    public void setOnClickListener(OnReportListener reportListener) {
        this.reportListener = reportListener;
    }
}
