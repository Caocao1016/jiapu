package com.demo.jiapu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.entity.AddGrjpRequest;
import com.demo.jiapu.entity.EditGrjpRequest;
import com.demo.jiapu.modle.AddMemberView;
import com.demo.jiapu.presenter.AddMemberPresenter;
import com.demo.jiapu.util.StringUtil;
import com.demo.jiapu.widget.MoreEditView;
import com.demo.jiapu.widget.SortCheckBoxView;
import com.demo.jiapu.widget.SwitchView;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.tools.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddMemberActivity extends BaseActivity<AddMemberPresenter> implements SwitchView.onClickCheckedListener, AddMemberView {

    @BindView(R.id.swv_add_live)
    SwitchView dieStatusView;
    @BindView(R.id.tb_add_title)
    TitleBar mTitleBar;
    @BindView(R.id.ll_add_not_live)
    LinearLayout unLiveLayout;
    @BindView(R.id.ll_add_live)
    LinearLayout liveLayout;
    @BindView(R.id.et_add_family_surname)
    EditText surEditView;
    @BindView(R.id.et_add_family_names)
    EditText namesEditView;
    @BindView(R.id.swv_add_sex)
    SwitchView sexView;
    @BindView(R.id.me_weixin)
    MoreEditView weixinView;
    @BindView(R.id.mev_add_family_seniority)
    MoreEditView seniorityView;
    @BindView(R.id.mev_add_family_sort)
    SortCheckBoxView sortView;
    @BindView(R.id.mev_add_family_native_place)
    MoreEditView placeView;
    @BindView(R.id.mev_add_family_birthday)
    MoreEditView birthdayView;
    @BindView(R.id.me_phone)
    MoreEditView phoneView;
    @BindView(R.id.mev_add_family_die_time)
    MoreEditView dieTimeView;
    @BindView(R.id.mev_add_family_burial_site)
    MoreEditView burialSiteView;
    @BindView(R.id.tv_add_who)
    TextView addWhoTextView;
    @BindView(R.id.civ_add_avatar)
    CircleImageView avatarView;
    @BindView(R.id.bt_add_family)
    Button button;


    private Intent intent;
    private FamilyBean familyBean;
    private int type;


    @Override
    protected AddMemberPresenter createPresenter() {
        return new AddMemberPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_family;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_add_title;
    }

    @Override
    public void init() {
        intent = getIntent();
        type = intent.getIntExtra("type", 0);
        familyBean = (FamilyBean) intent.getSerializableExtra("bean");
        super.init();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        mTitleBar.getRightView().setVisibility(type == 1 ? View.GONE : View.VISIBLE);

        button.setText(1 == type ? "添加" : "保存");
        button.setTag(type);

        dieStatusView.setOnClickCheckedListener(this);
        String name = StringUtil.isEmpty(familyBean.getNickname()) ? familyBean.getSurname() + familyBean.getNames() : familyBean.getNickname();
        addWhoTextView.setText("添加" + name + "的" + intent.getStringExtra("itemName"));

        if (type == 2) {
            Glide.with(this).load(familyBean.getMemberImg()).into(avatarView);
            addWhoTextView.setText(familyBean.getNickname());
            surEditView.setText(familyBean.getSurname());
            namesEditView.setText(familyBean.getNames());
            dieStatusView.setChecked(familyBean.getDie_status() == 2);
            weixinView.setText(familyBean.getNickname());
            sexView.setChecked(familyBean.getSex().equals("2"));
            seniorityView.setText(String.valueOf(familyBean.getSeniority()));
            sortView.setValue(familyBean.getSort());
            placeView.setText(familyBean.getNative_place());
            birthdayView.setText(familyBean.getBirthday());
            phoneView.setText(familyBean.getPhone());
            dieTimeView.setText(familyBean.getDietime());
            burialSiteView.setText(familyBean.getBurial_site());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(BaseResponse response) {
        Log.i("tag", response.getMsg());
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }


    @Override
    public void onLeftClick(View v) {
        //删除按钮
        super.onLeftClick(v);
    }

    @Override
    public void onClickChecked(boolean isChecked) {
        if (isChecked) {
            unLiveLayout.setVisibility(View.VISIBLE);
            liveLayout.setVisibility(View.GONE);
        } else {
            unLiveLayout.setVisibility(View.GONE);
            liveLayout.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.bt_add_family)
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case 1:
                AddGrjpRequest addGrjpRequest = new AddGrjpRequest();
                addGrjpRequest.birthday = birthdayView.getValue();
                addGrjpRequest.burialSite = burialSiteView.getValue();
                addGrjpRequest.create_time = System.currentTimeMillis();
                addGrjpRequest.dieStatus = dieStatusView.isChecked() ? 2 : 1;
                addGrjpRequest.dieTime = dieTimeView.getValue();
                addGrjpRequest.names = String.valueOf(namesEditView.getText());
                addGrjpRequest.nativePlace = placeView.getValue();
                addGrjpRequest.phone = phoneView.getValue();
                addGrjpRequest.sex = sexView.isChecked() ? 2 : 1;
                addGrjpRequest.seniority = seniorityView.getValue();
                addGrjpRequest.sort = sortView.getValue();
                addGrjpRequest.surName = String.valueOf(surEditView.getText());
                addGrjpRequest.typeId = intent.getStringExtra("itemID");
                addGrjpRequest.userId = familyBean.getMemberId();
                Log.e("Tag", familyBean.getMemberId());
                addGrjpRequest.isHave = 0;
                mPresenter.addMember(addGrjpRequest);
                break;
            case 2:
                EditGrjpRequest editGrjpRequest = new EditGrjpRequest();
                editGrjpRequest.birthday = birthdayView.getValue();
                editGrjpRequest.burialSite = burialSiteView.getValue();
                editGrjpRequest.dieStatus = dieStatusView.isChecked() ? 2 : 1;
                editGrjpRequest.dieTime = dieTimeView.getValue();
                editGrjpRequest.names = String.valueOf(namesEditView.getText());
                editGrjpRequest.surName = String.valueOf(surEditView.getText());
                editGrjpRequest.nativePlace = placeView.getValue();
                editGrjpRequest.phone = phoneView.getValue();
                editGrjpRequest.sex = sexView.isChecked() ? 2 : 1;
                editGrjpRequest.seniority = seniorityView.getValue();
                editGrjpRequest.sort = sortView.getValue();
                editGrjpRequest.userId = familyBean.getMemberId();
                mPresenter.editMember(editGrjpRequest);
                break;
        }

    }

}
