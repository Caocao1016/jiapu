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
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.DeferMoneyActivity;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.presenter.SettingAuthorizationPresenter;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.AuthorizeResponse;

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

public class MessageLeftFragment extends CommonLazyFragment<SettingAuthorizationPresenter> implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, SettingAuthorizaView {

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
    protected SettingAuthorizationPresenter createPresenter() {
        return new SettingAuthorizationPresenter(this);
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(DeferMoneyActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
//        mSwipe.setRefreshing(true);
//        AuthorRequest request = new AuthorRequest();
//        request.userId = RxSPTool.getString(getActivity(), Constant.USER_ID);
//        request.status = "0";
//        request.page = mPageNo;
//        request.limit = Constant.PAGE_SIZE;
//        mPresenter.myAuthorizeRecord(request);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
//        mAdapter.addData(strings);
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
    public void onSuccess(BaseResponse response) {

    }

    @Override
    public void onremoveSuccess(BaseResponse response) {

    }

    @Override
    public void onListSuccess(AuthorizeRecordResponse response) {
        if (null != response){
            getList(response.getList());
        }
    }

    @Override
    public void onRightSuccess(AuthorizeRecordResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onSFailure(BaseResponse response) {

    }

    @Override
    public void onremoveFailure(BaseResponse response) {

    }

    @Override
    public void onAuthorFailure(BaseResponse response) {

    }

    @Override
    public void onError() {

        mSwipe.setRefreshing(false);
    }


    public void getList(List<AuthorizeResponse> entity) {

//        if (mIsLoadMore) {
//            mIsLoadMore = false;
//            if (entity != null && entity.size() <= Constant.PAGE_SIZE && entity.size() > 0) {
//                mAdapter.addData(entity);
//                mPageNo++;
//                mAdapter.loadMoreComplete();
//            } else {
//                mAdapter.loadMoreEnd(true);
//            }
//        } else {
//            if (null != entity && entity.size()>0){
//                mPageNo++;
//                mAdapter.setNewData(entity);
//            }else {
//                mAdapter.setEmptyView(emptyView);
//            }
//        }
        mSwipe.setRefreshing(false);
    }

}
