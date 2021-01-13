package com.demo.jiapu.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.jiapu.R;

import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.CommonLazyFragment;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDbManger;
import com.demo.jiapu.dialog.MenuDialog;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.entity.evbus.InitFamilyDataEventbus;
import com.demo.jiapu.entity.evbus.OpenMemberTreeEventbus;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.modle.HomeLeftView;
import com.demo.jiapu.presenter.HomeLeftPresenter;
import com.demo.jiapu.widget.FamilyTreeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HomeLeftFragment extends CommonLazyFragment<HomeLeftPresenter> implements OnFamilyLongClickListener, HomeLeftView {
    private static final String MY_ID = "601";

    private MenuDialog menuDialog;
    private boolean isAddAndEdit = false;

    FamilyTreeView ftvTree;
    private FamilyBean drawBean;

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

//        String json = test.test;
//        List<FamilyBean> mList = JSONObject.parseArray(json, FamilyBean.class);
        SelGrjpRequest request = new SelGrjpRequest();
        request.userId = "5";
        mPresenter.getList(request);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onFamilyLongClick(FamilyBean familyBean) {
        boolean home = !TextUtils.isEmpty(familyBean.getMemberImg());
        boolean add = isAddAndEdit;
        boolean edit = isAddAndEdit;
        boolean open = familyBean.isOpen();
        boolean builder = false;
        if (!isAddAndEdit)
            builder = familyBean.getUser_id() == familyBean.getTx_userid();

        if (home || isAddAndEdit || open || builder) {
            menuDialog = new MenuDialog(getContext(), familyBean);
            menuDialog.show();

            menuDialog.setType(home, open, add, edit, builder);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void drawTree(OpenMemberTreeEventbus event) {
        FamilyDbManger dbHelper = new FamilyDbManger(MyApp.getInstance(), "myTree.db");
        ;
        ftvTree.drawFamilyTree(dbHelper.getTreeData(event.getMember()));
        dbHelper.closeDb();
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
        final FamilyBean my = dbHelper.getFamilyById("1");
        ftvTree.drawFamilyTree(dbHelper.getTreeData(my));
        dbHelper.closeDb();
    }


    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {
    }
}
