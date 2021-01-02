package com.demo.jiapu.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.test;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.CommonLazyFragment;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDBHelper;
import com.demo.jiapu.dialog.MenuDialog;
import com.demo.jiapu.entity.evbus.OpenMemberTreeEventbus;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.widget.FamilyTreeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class HomeLeftFragment extends CommonLazyFragment implements OnFamilyLongClickListener {
    private static final String MY_ID = "601";

    private MenuDialog menuDialog;

    FamilyTreeView ftvTree;

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        initView();
        initData();
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {
        ftvTree = findViewById(R.id.tv_ac_f_tree);
        String json = test.test;
        List<FamilyBean> mList = JSONObject.parseArray(json, FamilyBean.class);

        final FamilyDBHelper dbHelper = new FamilyDBHelper(MyApp.getInstance(), ftvTree.getDBName());
        dbHelper.save(mList);
        final FamilyBean my = dbHelper.findFamilyById(MY_ID);
        my.setSelect(true);
        dbHelper.closeDB();
        ftvTree.setCanClick(true);
        ftvTree.setShowBottomSpouse(false);
        ftvTree.drawFamilyTree(my);
        ftvTree.setOnFamilyLongClickListener(this);
        registerEventBus(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onFamilyLongClick(FamilyBean family) {

        menuDialog = new MenuDialog(getContext(), family);

        menuDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void drawTree(OpenMemberTreeEventbus event) {
        ftvTree.drawFamilyTree(event.getMember());
    }
}
