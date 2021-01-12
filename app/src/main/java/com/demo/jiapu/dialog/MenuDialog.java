package com.demo.jiapu.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.AddMemberActivity;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.entity.evbus.OpenMemberTreeEventbus;
import com.demo.jiapu.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MenuDialog extends BaseFullScreenDialog {

    @BindView(R.id.tv_menu_name)
    TextView textView;
    @BindView(R.id.iv_menu_avatar)
    ImageView imageView;
    @BindView(R.id.ll_menu_open)
    LinearLayout mLlMenuOpen;
    @BindView(R.id.ll_menu_home)
    LinearLayout mLlMenuHome;
    @BindView(R.id.ll_menu_builder)
    LinearLayout mLlMenuBuilder;
    @BindView(R.id.ll_menu_edit)
    LinearLayout mLlMenuEdit;
    @BindView(R.id.ll_menu_add)
    LinearLayout mLlMenuAdd;

    private FamilyBean familyBean;

    private static final int AVATAR_MALE = R.drawable.ic_avatar_male;//男性默认头像
    private static final int AVATAR_FEMALE = R.drawable.ic_avatar_female;//女性默认头像
    public static final int ITEM_ADD = 1;
    public static final int ITEM_EDIT = 2;
    public static final int ITEM_OPEN = 3;
    public static final int ITEM_HOME = 4;
    public static final int ITEM_BUILDER = 5;


    private static final String SEX_MALE = "1";//1为男性
    private static final String SEX_FEMALE = "2";//2为女性
    private Unbinder bind;

    public MenuDialog(Context context) {
        super(context);
    }

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
        bind = ButterKnife.bind(this);

        if (StringUtil.isEmpty(familyBean.getNickname()))
            textView.setText(familyBean.getSurname() + familyBean.getNames());
        else textView.setText(familyBean.getNickname());

        RequestOptions requestOptions = new RequestOptions()
                .circleCrop()
                .placeholder(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE)
                .error(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE).centerCrop()
                .dontAnimate();
        Glide.with(getContext())
                .load(familyBean.getMemberImg())
                .apply(requestOptions)
                .into(imageView);


    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.rl_menu_my, R.id.ll_menu_open, R.id.ll_menu_home, R.id.ll_menu_add,
            R.id.ll_menu_edit, R.id.rl_all, R.id.ll_menu_builder})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_menu_add:
                MenuAddDialog dialog = new MenuAddDialog(getContext(), familyBean);
                dialog.show();
                dismiss();
                break;

            case R.id.ll_menu_edit:
                Intent intent = new Intent(getContext(), AddMemberActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("bean", familyBean);
                getContext().startActivity(intent);
                dismiss();
                break;

            case R.id.ll_menu_home:


                dismiss();
                break;
            case R.id.ll_menu_open:
//
                EventBus.getDefault().post(new OpenMemberTreeEventbus(familyBean));
                dismiss();
                break;
            case R.id.ll_menu_builder:
                BuilderDialog dialog1 = new BuilderDialog(getContext());
                dialog1.show();
                dismiss();
                break;
            case R.id.rl_menu_my:
                break;
            case R.id.rl_all:
                dismiss();
                break;
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        bind.unbind();
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
}
