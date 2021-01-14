package com.demo.jiapu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.core.widget.PopupWindowCompat;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDbManger;
import com.demo.jiapu.dialog.JoinDialog;
import com.demo.jiapu.dialog.ReportDialog;
import com.demo.jiapu.dialog.TestLeftPopupWindow;
import com.demo.jiapu.entity.selSjjpRequest;
import com.demo.jiapu.listener.OnFamilyLongClickListener;
import com.demo.jiapu.modle.AutoMateView;
import com.demo.jiapu.modle.EditHomeView;
import com.demo.jiapu.presenter.EditHomePresenter;
import com.demo.jiapu.widget.FamilyTreeView;
import com.hjq.bar.TitleBar;

import java.util.List;

import butterknife.BindView;

public class EditHomeActivity extends BaseActivity<EditHomePresenter> implements OnFamilyLongClickListener, EditHomeView {

    FamilyTreeView ftvTree;
    @BindView(R.id.tb_title)
    TitleBar tbTitle;
    private long id = 2;
    private int type;


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

//        id = getIntent().getLongExtra("id", 0L);
        ftvTree = findViewById(R.id.tv_ac_f_tree);
        ftvTree.setOnFamilyLongClickListener(this);
        type = getIntent().getIntExtra("type", 0);
        tbTitle.setTitle(getIntent().getStringExtra("name"));
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        TestLeftPopupWindow mWindow = new TestLeftPopupWindow(this, type);
        mWindow.setOnClickListener(type -> {
            if (type == 0) {
                showReportDialog();
            } else {
                Intent intent = new Intent(this, AutoMateActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        PopupWindowCompat.showAsDropDown(mWindow, tbTitle.getRightView(), 0, 0, Gravity.BOTTOM);

    }

    private void showReportDialog() {

        ReportDialog reportDialog = new ReportDialog(this);
        reportDialog.setOnClickListener(type -> {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            startActivity(intent);
        });

        reportDialog.show();
    }


    @Override
    protected void initData() {
        selSjjpRequest request = new selSjjpRequest();
        request.list_id = id;
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
        final FamilyBean my = dbHelper.getFamilyById("10");
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
