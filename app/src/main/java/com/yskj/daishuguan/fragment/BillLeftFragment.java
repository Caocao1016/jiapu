package com.yskj.daishuguan.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.DeferMoneyActivity;
import com.yskj.daishuguan.activity.PaymentDetailsActivity;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.entity.request.AuthorRequest;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.modle.BillView;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.presenter.BillPresenter;
import com.yskj.daishuguan.presenter.SettingAuthorizationPresenter;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.AuthorizeResponse;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;
import com.yskj.daishuguan.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2018/12/7
 *
 * @ClassName: AuthorizationLeftActivity
 * @Description:
 */

public class BillLeftFragment  extends CommonLazyFragment<BillPresenter> implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BillView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    private BillAdapter mAdapter;
    private int mPageNo = 1;
    private View emptyView ;
    private boolean mIsLoadMore;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected BillPresenter createPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_authorization;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BillAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mSwipe.setRefreshing(true);
        ManagementListRequest request = new ManagementListRequest();
        request.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        request.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        request.type = "0";
        request.page = mPageNo;
        request.mobileno = RxSPTool.getString(getContext(), Constant.USER_MOBILENO);
        request.limit = Constant.PAGE_SIZE;
        mPresenter.creditList(request);

    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }


    @Override
    public void onSuccess(BillResponse response) {
        List<BillResponse.ListBean> entity = response.getList();
        if (mIsLoadMore) {
            mIsLoadMore = false;
            if (entity != null && entity.size() <= Constant.PAGE_SIZE && entity.size() > 0) {
                mAdapter.addData(entity);
                mPageNo++;
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd(true);
            }
        } else {
            if (null != entity && entity.size()>0){
                mPageNo++;
                mAdapter.setNewData(entity);
            }else {
                mAdapter.setEmptyView(emptyView);
            }
        }
        mSwipe.setRefreshing(false);
    }

    @Override
    public void onHuanKuanSuccess(BillHuankuanResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        mSwipe.setRefreshing(false);
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onError() {

        mSwipe.setRefreshing(false);
    }


}
