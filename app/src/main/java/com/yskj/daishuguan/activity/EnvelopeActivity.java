package com.yskj.daishuguan.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.adapter.EnvelopeAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: EnvelopeActivity
 * @Description: 优惠券
 */

public class EnvelopeActivity   extends BaseActivity implements  BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView ;


    private int mPageNo = 1;
    private EnvelopeAdapter mAdapter;
    private View emptyView ;
    private boolean mIsLoadMore;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_envelope;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_envelope_title;
    }

    @Override
    protected void initView() {
        emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EnvelopeAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        mAdapter.addData(strings);
    }

    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }
}
