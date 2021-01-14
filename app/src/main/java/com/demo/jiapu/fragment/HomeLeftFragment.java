package com.demo.jiapu.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.jiapu.R;

import com.demo.jiapu.activity.AddMemberActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.CommonLazyFragment;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.BuilderBean;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDbManger;
import com.demo.jiapu.dialog.BuilderDialog;
import com.demo.jiapu.dialog.MenuAddDialog;
import com.demo.jiapu.dialog.MenuDialog;
import com.demo.jiapu.entity.SelCjzRequest;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.entity.evbus.InitFamilyDataEventbus;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.modle.HomeLeftView;
import com.demo.jiapu.presenter.HomeLeftPresenter;
import com.demo.jiapu.widget.FamilyTreeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

public class HomeLeftFragment extends CommonLazyFragment<HomeLeftPresenter> implements OnFamilyLongClickListener, HomeLeftView {
    private String mId = "1";

    private boolean isAddAndEdit = true;

    private FamilyTreeView ftvTree;

    @Override
    protected HomeLeftPresenter createPresenter() {
        return new HomeLeftPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_tree;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerEventBus(this);
        initView();
        initData();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        ftvTree = findViewById(R.id.tv_ac_f_tree);
        ftvTree.setOnFamilyLongClickListener(this);
    }

    @Override
    protected void initData() {
        SelGrjpRequest request = new SelGrjpRequest();
        request.userId = "5";
        mPresenter.getList(request);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }

    @Override
    public void onFamilyLongClick(FamilyBean familyBean) {
        if (null == familyBean)
            return;
        boolean home = !TextUtils.isEmpty(familyBean.getMemberImg());
        boolean add = isAddAndEdit;
        boolean edit = isAddAndEdit;
        boolean open = familyBean.isOpen();
        boolean builder = false;
        if (!isAddAndEdit)
            builder = familyBean.getUser_id() == familyBean.getTx_userid();

        if (home || isAddAndEdit || open || builder) {
            MenuDialog menuDialog = new MenuDialog(getContext(), familyBean);
            menuDialog.setOnClickListener((type, bean) -> {
                switch (type) {
                    case R.id.ll_menu_add:
                        MenuAddDialog dialog = new MenuAddDialog(HomeLeftFragment.this.getContext(), bean);
                        dialog.show();
                        break;

                    case R.id.ll_menu_edit:
                        Intent intent = new Intent(HomeLeftFragment.this.getContext(), AddMemberActivity.class);
                        intent.putExtra("type", 2);
                        intent.putExtra("bean", bean);
                        Objects.requireNonNull(HomeLeftFragment.this.getContext()).startActivity(intent);
                        break;

                    case R.id.ll_menu_home:
                        //主页按钮

                        break;
                    case R.id.ll_menu_open:
                        FamilyDbManger dbHelper = new FamilyDbManger(MyApp.getInstance(), "myTree.db");
                        ftvTree.drawFamilyTree(dbHelper.getTreeData(bean));
                        dbHelper.closeDb();
                        break;
                    case R.id.ll_menu_builder:
                        SelCjzRequest selCjzRequest = new SelCjzRequest();
                        selCjzRequest.tx_userid = bean.getTx_userid();
                        mPresenter.getBuilderData(selCjzRequest);
                        break;

                    default:
                        break;
                }
            });
            menuDialog.show();
            menuDialog.setType(home, open, add, edit, builder);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addMember(InitFamilyDataEventbus event) {
        initData();
    }

    @Override
    public void onSuccess(List<FamilyBean> response) {
        FamilyDbManger dbHelper = new FamilyDbManger(MyApp.getInstance(), "myTree.db");
        dbHelper.deleteAll();
        dbHelper.save(response);
        final FamilyBean my = dbHelper.getFamilyById(mId);
        ftvTree.drawFamilyTree(dbHelper.getTreeData(my));
        dbHelper.closeDb();
    }


    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {
    }

    @Override
    public void onBuilderSuccess(BuilderBean builderBean) {
        BuilderDialog dialog1 = new BuilderDialog(getContext(), builderBean);
        dialog1.show();
    }
}
