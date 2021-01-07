package com.demo.jiapu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.CreateHomeActivity;
import com.demo.jiapu.activity.EditHomeActivity;
import com.demo.jiapu.adapter.HomeRightAdapter;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.base.CommonLazyFragment;
import com.demo.jiapu.bean.JpsjListDataBean;
import com.demo.jiapu.entity.ListRequest;
import com.demo.jiapu.entity.evbus.CreateHomeEventbus;
import com.demo.jiapu.entity.evbus.OpenMemberTreeEventbus;
import com.demo.jiapu.modle.HomeRigView;
import com.demo.jiapu.presenter.HomeRigPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRightFragment extends CommonLazyFragment<HomeRigPresenter> implements HomeRigView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private HomeRightAdapter mAdapter;

    private boolean mIsLoadMore;
    private int mPageNo = 1;

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
        registerEventBus(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new HomeRightAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(getContext(), EditHomeActivity.class)));


    }

    @Override
    protected void initData() {

        ListRequest listRequest = new ListRequest();
        listRequest.page = mPageNo;
        mPresenter.getList(listRequest);
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
        if (mIsLoadMore) {
            mIsLoadMore = false;
            if (response != null && response.size() <= 15 && response.size() > 0) {
                mAdapter.addData(response);
                mPageNo++;
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd(true);
            }
        } else {
            if (null != response && response.size() > 0) {
                mPageNo++;
                mAdapter.setNewData(response);
            } else {
                mAdapter.setNewData(null);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void createHome(CreateHomeEventbus event) {
        mPageNo = 1 ;
        initData();
    }


    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
}
