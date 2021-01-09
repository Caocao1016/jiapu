package com.demo.jiapu.activity;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import androidx.core.widget.PopupWindowCompat;

import com.alibaba.fastjson.JSONObject;
import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDBHelper;
import com.demo.jiapu.dialog.MenuDialog;
import com.demo.jiapu.dialog.TestLeftPopupWindow;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.widget.FamilyTreeView;
import com.hjq.bar.TitleBar;

import java.util.List;

import butterknife.BindView;

public class EditHomeActivity extends BaseActivity implements OnFamilyLongClickListener {
    private static final String MY_ID = "601";

    private MenuDialog menuDialog;
    FamilyTreeView ftvTree;
    @BindView(R.id.tb_title)
    TitleBar tbTitle;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_home;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {

        ftvTree = findViewById(R.id.tv_ac_f_tree);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        TestLeftPopupWindow mWindow = new TestLeftPopupWindow(this);
//根据指定View定位
        PopupWindowCompat.showAsDropDown(mWindow, tbTitle.getRightView(), 0, 0, Gravity.BOTTOM);

    }


    @Override
    protected void initData() {
//        String json = test.test;
//        List<FamilyBean> mList = JSONObject.parseArray(json, FamilyBean.class);
//
//        final FamilyDBHelper dbHelper = new FamilyDBHelper(MyApp.getInstance(),ftvTree.getDBName());
//        dbHelper.save(mList);
//        final FamilyBean my = dbHelper.findFamilyById(MY_ID);
//        my.setSelect(true);
//        dbHelper.closeDB();
//
//        ftvTree.setShowBottomSpouse(false);
//        ftvTree.drawFamilyTree(my);
//        ftvTree.setOnFamilyLongClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ftvTree.destroyView();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onFamilyLongClick(FamilyBean family) {
        menuDialog = new MenuDialog(this, family);
        menuDialog.show();
    }


}
