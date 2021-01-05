package com.demo.jiapu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.CreateHomeActivity;
import com.demo.jiapu.activity.EditHomeActivity;
import com.demo.jiapu.adapter.HomeRightAdapter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.CommonLazyFragment;
import com.demo.jiapu.bean.JpsjListDataBean;
import com.demo.jiapu.modle.HomeRigView;
import com.demo.jiapu.presenter.HomeRigPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRightFragment extends CommonLazyFragment<HomeRigPresenter> implements HomeRigView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private HomeRightAdapter mAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }
    @Override
    protected HomeRigPresenter createPresenter() {
        return new HomeRigPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_rig;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new HomeRightAdapter(null);
    }

    @Override
    protected void initData() {

        mPresenter.getList();
//        mPresenter.addJpsj();
    }

    @OnClick({R.id.baner})
    public void onClick(View view) {
        if (view.getId() == R.id.baner) {

            startActivity(new Intent(getContext(), CreateHomeActivity.class));
        }
    }

    @Override
    public void onSuccess(List<JpsjListDataBean> response) {

        mAdapter.setNewData(response);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(getContext(), EditHomeActivity.class)));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }
}
