package com.demo.jiapu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.jiapu.R;
import com.demo.jiapu.activity.CreateHomeActivity;
import com.demo.jiapu.activity.SearchActivity;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.base.CommonLazyFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRightFragment extends CommonLazyFragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.rb_left)
    RadioButton rb_left;
    @BindView(R.id.rb_right)
    RadioButton rb_right;
    @BindView(R.id.rgTools)
    RadioGroup rg_all;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        rg_all.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_left:
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.rb_right:
                    mViewPager.setCurrentItem(1, false);
                    break;
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager(), 0) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new HomeMeFragment();
                        break;
                    case 1:
                        fragment = new HomeCreateFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rg_all.check(rg_all.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void initData() {
    }


    @OnClick({R.id.baner, R.id.ll_search})
    public void onClick(View view) {
        if (view.getId() == R.id.baner) {
            startActivity(new Intent(getContext(), CreateHomeActivity.class));
        } else if (view.getId() == R.id.ll_search) {
            startActivity(new Intent(getContext(), SearchActivity.class));
        }
    }


}
