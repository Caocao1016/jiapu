package com.demo.jiapu.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.CreatHomeActivity;
import com.demo.jiapu.activity.EditHomeActivity;
import com.demo.jiapu.adapter.HomeRightAdapter;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.CommonLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRightFragment extends CommonLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private HomeRightAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        List<String> mLust= new ArrayList<>();
        mLust.add("1");
        mLust.add("1");
        mLust.add("1");
        mLust.add("1");
        mLust.add("1");
        mAdapter.setNewData(mLust);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(getContext(), EditHomeActivity.class)));

    }

    @OnClick({R.id.baner})
    public void onClick(View view) {
        if (view.getId() == R.id.baner) {
            startActivity(new Intent(getContext(), CreatHomeActivity.class));
        }
    }

}
