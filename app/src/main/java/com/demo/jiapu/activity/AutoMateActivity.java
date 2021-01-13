package com.demo.jiapu.activity;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.MyApp;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.db.FamilyDbManger;
import com.demo.jiapu.entity.SelGrjpRequest;
import com.demo.jiapu.entity.ZdppRequest;
import com.demo.jiapu.modle.AutoMateView;
import com.demo.jiapu.presenter.AutoMatePresenter;
import com.demo.jiapu.widget.FamilyTreeView;

import java.util.List;

public class AutoMateActivity extends BaseActivity<AutoMatePresenter> implements AutoMateView {

    private FamilyTreeView ftvTree;

    @Override
    protected AutoMatePresenter createPresenter() {
        return new AutoMatePresenter(this);
    }

    @Override
    public void onSuccess(String id, List<FamilyBean> response) {
        FamilyDbManger dbHelper = new FamilyDbManger(MyApp.getInstance(), "myTree3.db");
        dbHelper.deleteAll();
        dbHelper.save(response);
        final FamilyBean my = dbHelper.getFamilyById(id);
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
    protected int getLayoutId() {
        return R.layout.activity_auto_mate;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_auto_mate;
    }

    @Override
    protected void initView() {
        ftvTree = findViewById(R.id.tv_ac_f_tree);
    }

    @Override
    protected void initData() {
        ZdppRequest request = new ZdppRequest();
        request.list_id = "5";
        mPresenter.getList(request);

    }
}
