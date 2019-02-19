package com.yskj.daishuguan.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.adapter.EnvelopeAdapter;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
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
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: EnvelopeActivity
 * @Description: 优惠券
 */

public class EnvelopeActivity extends BaseActivity<ManagementMoneyPresenter> implements
        BaseQuickAdapter.RequestLoadMoreListener, ManagementMoneyView {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private int mPageNo = 1;
    private EnvelopeAdapter mAdapter;
    private View emptyView;
    private boolean mIsLoadMore;

    @Override
    protected ManagementMoneyPresenter createPresenter() {
        return new ManagementMoneyPresenter(this);
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ManagementListItemResponse> entity = adapter.getData();

                for (ManagementListItemResponse mList : entity) {
                    mList.setSelect(!entity.get(position).isSelect());
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }


    @OnClick({R.id.tv_sure})
    public void onClick(View view){
        if (view.getId() == R.id.tv_sure){
            List<ManagementListItemResponse> entity = mAdapter.getData();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.reverse();
            for (ManagementListItemResponse mList :entity){
                if (mList.isSelect()){
                    stringBuilder.append(mList.getId()+",");
                }
            }
            Intent intent = new Intent();
            intent.putExtra("mListID",stringBuilder.toString());
            setResult(2,intent);
            finish();
        }

    }

    @Override
    protected void initData() {
        ManagementListRequest request = new ManagementListRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        request.type = "1";
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
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIUtils.showToast(response.getRetmsg());
    }

    @Override
    public void onError() {

    }
}
