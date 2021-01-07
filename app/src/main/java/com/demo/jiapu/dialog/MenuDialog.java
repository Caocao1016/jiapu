package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.AddMemberActivity;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.entity.evbus.OpenMemberTreeEventbus;

import org.greenrobot.eventbus.EventBus;


public class MenuDialog extends BaseFullScreenDialog implements View.OnClickListener {

    private FamilyBean familyBean;

    private static final int AVATAR_MALE = R.drawable.ic_avatar_male;//男性默认头像
    private static final int AVATAR_FEMALE = R.drawable.ic_avatar_female;//女性默认头像

    private static final String SEX_MALE = "1";//1为男性
    private static final String SEX_FEMALE = "2";//2为女性

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

        final TextView textView = findViewById(R.id.tv_menu_name);
        if (!familyBean.getNickname().equals(""))
            textView.setText(familyBean.getNickname());
        else textView.setText(familyBean.getSurname() + familyBean.getNames());
        final ImageView imageView = findViewById(R.id.iv_menu_avatar);

        RequestOptions requestOptions = new RequestOptions()
                .circleCrop()
                .placeholder(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE)
                .error(SEX_FEMALE.equals(familyBean.getSex()) ? AVATAR_FEMALE : AVATAR_MALE).centerCrop()
                .dontAnimate();
        Glide.with(getContext())
                .load(familyBean.getMemberImg())
                .apply(requestOptions)
                .into(imageView);

        findViewById((R.id.rl_menu_my)).setOnClickListener(this);
        findViewById(R.id.ll_menu_open).setOnClickListener(this);
        findViewById(R.id.ll_menu_home).setOnClickListener(this);
        findViewById(R.id.ll_menu_add).setOnClickListener(this);
        findViewById(R.id.ll_menu_edit).setOnClickListener(this);
        getContentView().setOnClickListener(this);
    }


    @Override
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
                EventBus.getDefault().post(new OpenMemberTreeEventbus(familyBean));
                dismiss();
                break;
            case R.id.rl_menu_my:
                break;
            default:
                dismiss();
                break;
        }

    }

}
