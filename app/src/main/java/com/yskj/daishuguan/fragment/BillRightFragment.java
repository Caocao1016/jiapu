package com.yskj.daishuguan.fragment;

import android.content.Intent;
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
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.activity.Defer2MoneyActivity;
import com.yskj.daishuguan.activity.DeferMoneyActivity;
import com.yskj.daishuguan.activity.MembersActivity;
import com.yskj.daishuguan.activity.OverdueDetailsActivity;
import com.yskj.daishuguan.activity.PaymentDetailsActivity;
import com.yskj.daishuguan.adapter.BillAdapter;
import com.yskj.daishuguan.adapter.BillHuankuanAdapter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.entity.evbus.LoginEvbusBean;
import com.yskj.daishuguan.entity.request.AuthorRequest;
import com.yskj.daishuguan.entity.request.HuanKuanRequest;
import com.yskj.daishuguan.modle.BillView;
import com.yskj.daishuguan.modle.SettingAuthorizaView;
import com.yskj.daishuguan.presenter.BillPresenter;
import com.yskj.daishuguan.presenter.SettingAuthorizationPresenter;
import com.yskj.daishuguan.response.AuthorizeRecordResponse;
import com.yskj.daishuguan.response.AuthorizeResponse;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class BillRightFragment extends CommonLazyFragment<BillPresenter> implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BillView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    private BillHuankuanAdapter mAdapter;
    private int mPageNo = 1;
    private boolean mIsLoadMore;
    private String mStart = "1";
    private View emptyView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginEvbusBean(LoginEvbusBean event) {
        mPageNo = 1;
        initData();
    }

    @Override
    protected void initView() {

        mSwipe.setOnRefreshListener(this);
        emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mSwipe.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BillHuankuanAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                List<BillHuankuanResponse.ListBean> data = adapter.getData();

                if (data.get(position).getStatus() == 0) {
                    if (data.get(position).getIdDued().equals("1")) {  //逾期
                        Intent intent = new Intent(getContext(), OverdueDetailsActivity.class);
                        intent.putExtra("false", true);
                        intent.putExtra("interestRate", data.get(position).getInterestRate());
                        intent.putExtra("loanDate", data.get(position).getLoanDate());
                        intent.putExtra("loanOrderNo", data.get(position).getLoanOrderNo());
                        intent.putExtra("repayOrderNo", data.get(position).getRepayOrderNo());
                        intent.putExtra("duedDay", data.get(position).getDuedDay());;
                        startActivity(intent);
                    } else {  //代还款
                        Intent intent = new Intent(getContext(), PaymentDetailsActivity.class);
                        intent.putExtra("interestRate", data.get(position).getInterestRate());
                        intent.putExtra("loanDate", data.get(position).getLoanDate());
                        intent.putExtra("paymentDay", data.get(position).getPaymentDay());
                        intent.putExtra("loanOrderNo", data.get(position).getLoanOrderNo());
                        intent.putExtra("repayOrderNo", data.get(position).getRepayOrderNo());
                        intent.putExtra("duedDay", data.get(position).getDuedDay());
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getContext(), DeferMoneyActivity.class);
                    intent.putExtra("false", false);
                    intent.putExtra("interestRate", data.get(position).getInterestRate());
                    intent.putExtra("loanDate", data.get(position).getLoanDate());
                    intent.putExtra("loanOrderNo", data.get(position).getLoanOrderNo());
                    intent.putExtra("repayOrderNo", data.get(position).getRepayOrderNo());
                    startActivity(intent);
                }

//                1.逾期  2.展期

//

            }
        });
    }

    @Override
    protected void initData() {
        mSwipe.setRefreshing(true);
        HuanKuanRequest request = new HuanKuanRequest();
        request.userid = RxSPTool.getString(getActivity(), Constant.USER_ID);
        request.token = RxSPTool.getString(getActivity(), Constant.TOKEN);
        request.type = "0";
        request.page = mPageNo;
        request.limit = Constant.PAGE_SIZE;
        mPresenter.bills(request);

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

    }

    @Override
    public void onHuanKuanSuccess(BillHuankuanResponse response) {
        if (null != response) {
            getList(response.getList());
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }


    @Override
    public void onError() {

        mSwipe.setRefreshing(false);
    }

    public void showDialog(BillHuankuanResponse.ListBean entity) {
        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(getContext());
        rxDialogSureCancel.getTitleView().setVisibility(View.GONE);
        rxDialogSureCancel.getSureView().setTextColor(Color.parseColor("#007AFF"));
        rxDialogSureCancel.getCancelView().setTextColor(Color.parseColor("#007AFF"));

        rxDialogSureCancel.getContentView().setText("您有一笔贷款即将到达还款\n日可以选择申请展期延后还款！");
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Defer2MoneyActivity.class);
                intent.putExtra("interestRate", entity.getInterestRate());
                intent.putExtra("loanDate", entity.getLoanDate());
                intent.putExtra("paymentDay", entity.getPaymentDay());
                intent.putExtra("loanOrderNo", entity.getLoanOrderNo());
                intent.putExtra("repayOrderNo", entity.getRepayOrderNo());
                intent.putExtra("duedDay", entity.getDuedDay());
                startActivity(intent);
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.show();
    }


    public void getList(List<BillHuankuanResponse.ListBean> entity) {

//        if (entity != null && entity.size() > 0) {
//            if (entity.get(0).getStatus() == 0 && !entity.get(0).getIdDued().equals("1") && entity.get(0).getPaymentDay().equals("1")) {
//                showDialog(entity.get(0));
//            }
//
//        }
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
}
