package com.yskj.daishuguan.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.DeferMoneyActivity;
import com.yskj.daishuguan.adapter.OverdueAdapter;
import com.yskj.daishuguan.adapter.UnUesrAdapter;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.modle.ManagementMoneyView;
import com.yskj.daishuguan.presenter.ManagementMoneyPresenter;
import com.yskj.daishuguan.response.ManagementListItemResponse;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;
import com.yskj.daishuguan.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: OverdueFragment
 * @Description: 已过期
 */

public class OverdueFragment extends CommonLazyFragment <ManagementMoneyPresenter> implements
        BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener, ManagementMoneyView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    private int mPageNo = 1;
    private View emptyView;
    private boolean mIsLoadMore;
    private OverdueAdapter mAdapter;

    @Override
    protected ManagementMoneyPresenter createPresenter() {
        return new ManagementMoneyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_used;
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
        mAdapter = new OverdueAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

        ManagementListRequest request = new ManagementListRequest();
        request.userid = RxSPTool.getString(getContext(), Constant.USER_ID);
        request.token = RxSPTool.getString(getContext(), Constant.TOKEN);
        request.type = "3";
        request.page = mPageNo;
        request.limit = 10;
        mPresenter.couponUse(request);
    }

    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        initData();
    }

    @Override
    public void onSuccess(ManagementResponse response) {

    }

    @Override
    public void onCouponUseSuccess(ManagementListResponse response) {

        List<ManagementListItemResponse> entity = response.getList();
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
            if (null != entity && entity.size() > 0) {
                mPageNo++;
                mAdapter.setNewData(entity);
            } else {
                mAdapter.setEmptyView(emptyView);
            }
        }
        mSwipe.setRefreshing(false);
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
