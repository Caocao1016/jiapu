package com.yskj.daishuguan.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.hjq.baselibrary.base.BaseFragmentPagerAdapter;
import com.yskj.daishuguan.base.CommonLazyFragment;
import com.yskj.daishuguan.fragment.BillFragment;
import com.yskj.daishuguan.fragment.HomeNewFragment;
import com.yskj.daishuguan.fragment.MyFragment;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/20
 *
 * @ClassName: HomeViewPagerAdapter
 * @Description:主页界面 ViewPager + Fragment 适配器
 */

public final class HomeViewPagerAdapter extends BaseFragmentPagerAdapter<CommonLazyFragment> {

    public HomeViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager fm, List<CommonLazyFragment> list) {
        list.add(HomeNewFragment.newInstance());
        list.add(BillFragment.newInstance());
        list.add(MyFragment.newInstance());
    }
}

