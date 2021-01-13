package com.demo.jiapu.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.jiapu.R;
import com.demo.jiapu.adapter.HomeRightAdapter;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BaseResponse;
import com.demo.jiapu.bean.JpsjListDataBean;
import com.demo.jiapu.entity.ListRequest;
import com.demo.jiapu.modle.HomeRigView;
import com.demo.jiapu.presenter.HomeRigPresenter;
import java.util.List;
import butterknife.BindView;

public class SearchActivity extends BaseActivity<HomeRigPresenter> implements HomeRigView,
        BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.edit_text)
    EditText mEditText;
    private HomeRightAdapter mAdapter;

    private boolean mIsLoadMore;
    private int mPageNo = 1;


    @Override
    public void onLoadMoreRequested() {
        mIsLoadMore = true;
        initData();
    }

    @Override
    protected HomeRigPresenter createPresenter() {
        return new HomeRigPresenter(this);
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

    @Override
    public void onEditZhidingSuccess(String response) {

    }

    @Override
    public void onHomeSuccess(List<JpsjListDataBean> response) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_home;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new HomeRightAdapter(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
        {
            JpsjListDataBean item = mAdapter.getItem(position);
            Intent intent = new Intent(
                    this, EditHomeActivity.class);
            intent.putExtra("id",item.getId());
            intent.putExtra("name",item.getTitle());
            startActivity(intent);
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPageNo = 1;
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        ListRequest listRequest = new ListRequest();
        listRequest.page = mPageNo;
        listRequest.title = mEditText.getText().toString();
        mPresenter.getList(listRequest);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
