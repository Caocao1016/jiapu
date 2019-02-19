package com.yskj.daishuguan.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.MyShareListAdapter;
import com.yskj.daishuguan.adapter.UnUesrAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.ManagementListRequest;
import com.yskj.daishuguan.entity.request.ShareRequest;
import com.yskj.daishuguan.modle.ShareView;
import com.yskj.daishuguan.presenter.SharePresenter;
import com.yskj.daishuguan.response.ManagementListItemResponse;
import com.yskj.daishuguan.response.ShareContentResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.response.ShareResponse;
import com.yskj.daishuguan.util.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/16
 *
 * @ClassName: MyShareListActivity
 * @Description: 邀请记录
 */

public class MyShareListActivity extends BaseActivity<SharePresenter> implements ShareView,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    private int mPageNo = 1;
    private View emptyView;
    private boolean mIsLoadMore;
    private MyShareListAdapter mAdapter;

    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_share_list;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_share_lis_title;
    }

    @Override
    protected void initView() {
        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyShareListAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        ShareRequest request = new ShareRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        request.merchantCode = Constant.merchantcode;
        request.page = mPageNo;
        request.limit = 10;
        mPresenter.inviteRecord(request);
    }

    @Override
    public void onSuccess(ShareResponse response) {

    }

    @Override
    public void onRecordSuccess(ShareListResponse response) {
        List<ShareListResponse.ListBean> entity = response.getList();
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
    public void onShareSuccess(ShareContentResponse response) {

    }

    @Override
    public void onError() {
        mSwipe.setRefreshing(false);
    }

    @Override
    public void onFailure(BaseResponse response) {
        mSwipe.setRefreshing(false);
        UIUtils.showToast(response.getRetmsg());
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
}
