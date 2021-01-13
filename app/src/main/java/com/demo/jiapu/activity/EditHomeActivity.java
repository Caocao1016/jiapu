package com.demo.jiapu.activity;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import androidx.core.widget.PopupWindowCompat;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDbManger;
import com.demo.jiapu.dialog.MenuDialog;
import com.demo.jiapu.dialog.TestLeftPopupWindow;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.modle.EditHomeView;
import com.demo.jiapu.presenter.EditHomePresenter;
import com.demo.jiapu.widget.FamilyTreeView;
import com.hjq.bar.TitleBar;

import java.util.List;

import butterknife.BindView;

public class EditHomeActivity extends BaseActivity<EditHomePresenter> implements OnFamilyLongClickListener, EditHomeView {
    private static final String MY_ID = "601";

    private MenuDialog menuDialog;
    FamilyTreeView ftvTree;
    @BindView(R.id.tb_title)
    TitleBar tbTitle;

    @Override
    protected EditHomePresenter createPresenter() {
        return new EditHomePresenter(this);
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
        ftvTree.setOnFamilyLongClickListener(this);
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
        SelGrjpRequest request = new SelGrjpRequest();

        request.userId = "5";
        mPresenter.getList(request);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onFamilyLongClick(FamilyBean familyBean) {


    }


    @Override
    public void onSuccess(List<FamilyBean> response) {
        FamilyDbManger dbHelper = new FamilyDbManger(MyApp.getInstance(), "myTree2.db");
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
