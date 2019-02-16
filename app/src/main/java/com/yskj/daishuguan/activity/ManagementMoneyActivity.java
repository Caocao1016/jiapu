package com.yskj.daishuguan.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.vondear.rxtool.RxSPTool;
import com.yskj.daishuguan.Constant;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.base.BaseResponse;
import com.yskj.daishuguan.entity.request.OCRRequest;
import com.yskj.daishuguan.fragment.OverdueFragment;
import com.yskj.daishuguan.fragment.UnusedFragment;
import com.yskj.daishuguan.fragment.UsedFragment;
import com.yskj.daishuguan.modle.ManagementMoneyView;
import com.yskj.daishuguan.presenter.ManagementMoneyPresenter;
import com.yskj.daishuguan.response.ManagementListResponse;
import com.yskj.daishuguan.response.ManagementResponse;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * CaoPengFei
 * 2019/2/12
 *
 * @ClassName: CouponActivity
 * @Description: 红包管理
 */

public class ManagementMoneyActivity extends BaseActivity<ManagementMoneyPresenter> implements ManagementMoneyView {

    @BindView(R.id.order_tab)
    public TabLayout mTablay;
    @BindView(R.id.order_vp)
    public ViewPager mViewPager;

    @Override
    protected ManagementMoneyPresenter createPresenter() {
        return new ManagementMoneyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_management_moeny;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_money_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        rxDialogLoading.show();
        OCRRequest request = new OCRRequest();
        request.userid = RxSPTool.getString(this, Constant.USER_ID);
        request.token = RxSPTool.getString(this, Constant.TOKEN);
        mPresenter.couponTotal(request);
    }

    @Override
    public void onSuccess(ManagementResponse response) {
        rxDialogLoading.dismiss();
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("未使用(" + response.getNotUsed() + ")");
        titleDatas.add("已使用(" + response.getAlreadyUsed() + ")");
        titleDatas.add("已过期(" + response.getOverdueUsed() + ")");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new UnusedFragment());
        fragmentList.add(new UsedFragment());
        fragmentList.add(new OverdueFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        mViewPager.setAdapter(myViewPageAdapter);
        mTablay.setupWithViewPager(mViewPager);
        mTablay.setTabsFromPagerAdapter(myViewPageAdapter);
    }

    @Override
    public void onCouponUseSuccess(ManagementListResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {
        rxDialogLoading.dismiss();
    }

    @Override
    public void onError() {

    }


    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titleList;
        private ArrayList<Fragment> fragmentList;

        public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

}
