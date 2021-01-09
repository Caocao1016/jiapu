package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.iv_open)
    ImageView mIvOpen;

    @BindView(R.id.ll_menu_home)
    LinearLayout mLlMenuHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.iv_home)
    ImageView mIvHome;


    @BindView(R.id.ll_menu_edit)
    LinearLayout mLlMenuEdit;
    @BindView(R.id.ll_menu_add)
    LinearLayout mLlMenuAdd;

    private FamilyBean familyBean;

    private static final int AVATAR_MALE = R.drawable.ic_avatar_male;//男性默认头像
    private static final int AVATAR_FEMALE = R.drawable.ic_avatar_female;//女性默认头像

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

    @OnClick({R.id.rl_menu_my, R.id.ll_menu_open, R.id.ll_menu_home, R.id.ll_menu_add,
            R.id.ll_menu_edit, R.id.rl_all})
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

//                if ("主页"){
//
//                }else if ("创建者"){
//
//                }else if ("打开"){
//
//                }

                dismiss();
                break;
            case R.id.ll_menu_open:
//                if ("打开"){
//
//                }else if ("主页"){
//
//                }else if ("创建者"){
//
//                }

                EventBus.getDefault().post(new OpenMemberTreeEventbus(familyBean));
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

//    private void setType() {
//        if ("四个全部展示"){
//            mLlMenuAdd.setVisibility(View.VISIBLE);
//            mLlMenuEdit.setVisibility(View.VISIBLE);
//            mLlMenuHome.setVisibility(View.VISIBLE);
//            mLlMenuOpen.setVisibility(View.VISIBLE);
//        }else if ("隐藏打开"){
//            mLlMenuAdd.setVisibility(View.VISIBLE);
//            mLlMenuEdit.setVisibility(View.VISIBLE);
//            mLlMenuHome.setVisibility(View.VISIBLE);
//        }else if ("打开，创建着"){
//            mLlMenuOpen.setVisibility(View.VISIBLE);
//            mLlMenuHome.setVisibility(View.VISIBLE);
//            mIvOpen.setImageResource(R.drawable.ic_dialog_open);
//            mTvOpen.setText("打开");
//            mIvHome.setImageResource(R.drawable.ic_create);
//            mTvHome.setText("创建者");
//
//        }else if ("主页，打开"){
//            mLlMenuOpen.setVisibility(View.VISIBLE);
//            mLlMenuHome.setVisibility(View.VISIBLE);
//            mIvHome.setImageResource(R.drawable.ic_dialog_open);
//            mTvHome.setText("打开");
//            mIvOpen.setImageResource(R.drawable.ic_dialog_home);
//            mTvOpen.setText("主页");
//
//        }else if ("主页"){
//            mLlMenuOpen.setVisibility(View.VISIBLE);
//            mIvOpen.setImageResource(R.drawable.ic_dialog_home);
//            mTvOpen.setText("主页");
//
//        }else if ("创建者"){
//            mLlMenuOpen.setVisibility(View.VISIBLE);
//            mIvOpen.setImageResource(R.drawable.ic_create);
//            mTvOpen.setText("创建者");
//
//        }
//    }
}
