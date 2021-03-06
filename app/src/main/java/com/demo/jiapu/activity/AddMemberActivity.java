package com.demo.jiapu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.dialog.SelectPhotoDialog;
import com.demo.jiapu.entity.AddGrjpRequest;
import com.demo.jiapu.entity.EditGrjpRequest;
import com.demo.jiapu.entity.evbus.InitFamilyDataEventbus;
import com.demo.jiapu.modle.AddMemberView;
import com.demo.jiapu.presenter.AddMemberPresenter;
import com.demo.jiapu.util.StringUtil;
import com.demo.jiapu.widget.MoreEditView;
import com.demo.jiapu.widget.SortCheckBoxView;
import com.demo.jiapu.widget.SwitchView;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;

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
    private int sex;
    private int typeDelete;

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
        sex = intent.getIntExtra("sex", 1);
        typeDelete = intent.getIntExtra("typeDelete", 0);
        familyBean = (FamilyBean) intent.getSerializableExtra("bean");
        super.init();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {


        button.setText(1 == type ? "添加" : "保存");
        button.setTag(type);
        dieTimeView.setFocusable();
        birthdayView.setFocusable();
        dieStatusView.setOnClickCheckedListener(this);
        String str = StringUtil.isEmpty(familyBean.getNickname()) ? familyBean.getSurname() + familyBean.getNames() : familyBean.getNickname();
        sexView.setChecked(sex != 1);
        if (type == 2) {
            mTitleBar.getRightView().setVisibility(typeDelete == 1 ? View.GONE : View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.ic_head)
                    .error(R.drawable.ic_head).centerCrop()
                    .dontAnimate();
            Glide.with(this)
                    .load(familyBean.getMemberImg())
                    .apply(requestOptions)
                    .into(avatarView);
            addWhoTextView.setText("编辑" + str);
            surEditView.setText(familyBean.getSurname());
            namesEditView.setText(familyBean.getNames());
            dieStatusView.setChecked(familyBean.getDie_status() == 2);
            sexView.setChecked(familyBean.getSex().equals("2"));
            seniorityView.setText(String.valueOf(familyBean.getSeniority()));
            sortView.setValue(familyBean.getSort());
            placeView.setText(familyBean.getNative_place());
            birthdayView.setText(familyBean.getBirthday());
            phoneView.setText(familyBean.getPhone());
            dieTimeView.setText(familyBean.getDietime());
            burialSiteView.setText(familyBean.getBurial_site());
        } else {
            addWhoTextView.setText("添加" + str + "的" + intent.getStringExtra("itemName"));
            mTitleBar.getRightView().setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String response) {
        ToastUtils.s(this, response);
        EventBus.getDefault().post(new InitFamilyDataEventbus());
        finish();
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

    @OnClick({R.id.bt_add_family, R.id.mev_add_family_birthday, R.id.mev_add_family_die_time})
    public void onClick(View v) {
        if (v.getId() == R.id.bt_add_family) {
            if (1 == type) {

                if (TextUtils.isEmpty(surEditView.getText().toString())) {
                    ToastUtils.s(this, "请填写姓氏");
                    return;
                }

                if (TextUtils.isEmpty(namesEditView.getText().toString())) {
                    ToastUtils.s(this, "请填写名字");
                    return;
                }

                AddGrjpRequest addGrjpRequest = new AddGrjpRequest();

                addGrjpRequest.birthday = StringUtil.stringToLong(birthdayView.getValue());
                addGrjpRequest.dieTime = StringUtil.stringToLong(dieTimeView.getValue());
                addGrjpRequest.burialSite = burialSiteView.getValue();
                addGrjpRequest.create_time = System.currentTimeMillis();
                addGrjpRequest.dieStatus = dieStatusView.isChecked() ? 2 : 1;
                addGrjpRequest.names = namesEditView.getText().toString();
                addGrjpRequest.nativePlace = placeView.getValue();
                addGrjpRequest.phone = phoneView.getValue();
                addGrjpRequest.sex = sexView.isChecked() ? 2 : 1;
                addGrjpRequest.seniority = seniorityView.getValue();
                addGrjpRequest.sort = sortView.getValue();
                addGrjpRequest.surName = surEditView.getText().toString();
                addGrjpRequest.typeId = intent.getStringExtra("itemID");
                addGrjpRequest.userId = familyBean.getMemberId();
                Log.e("Tag", familyBean.getMemberId());
                addGrjpRequest.isHave = 0;
                mPresenter.addMember(addGrjpRequest);
            } else {

                if (TextUtils.isEmpty(surEditView.getText().toString())) {
                    ToastUtils.s(this, "请填写姓氏");
                    return;
                }

                if (TextUtils.isEmpty(namesEditView.getText().toString())) {
                    ToastUtils.s(this, "请填写名字");
                    return;
                }
                EditGrjpRequest editGrjpRequest = new EditGrjpRequest();

                editGrjpRequest.birthday = StringUtil.stringToLong(birthdayView.getValue());
                editGrjpRequest.dieTime = StringUtil.stringToLong(dieTimeView.getValue());
                editGrjpRequest.burialSite = burialSiteView.getValue();
                editGrjpRequest.dieStatus = dieStatusView.isChecked() ? 2 : 1;
                editGrjpRequest.names = namesEditView.getText().toString();
                editGrjpRequest.surName = surEditView.getText().toString();
                editGrjpRequest.nativePlace = placeView.getValue();
                editGrjpRequest.phone = phoneView.getValue();
                editGrjpRequest.sex = sexView.isChecked() ? 2 : 1;
                editGrjpRequest.seniority = seniorityView.getValue();
                editGrjpRequest.sort = sortView.getValue();
                editGrjpRequest.userId = familyBean.getMemberId();
                mPresenter.editMember(editGrjpRequest);
            }
        } else if (v.getId() == R.id.mev_add_family_birthday) {
            createDialog(0);
        } else if (v.getId() == R.id.mev_add_family_die_time) {
            createDialog(1);
        }
    }

    private void createDialog(int type) {
        SelectPhotoDialog selectPhotoDialog = new SelectPhotoDialog(this);
        selectPhotoDialog.setOnClickListener(date -> {
            if (type == 0) {
                birthdayView.setText(date);
            } else {
                dieTimeView.setText(date);
            }
        });
        selectPhotoDialog.show();

    }

}
