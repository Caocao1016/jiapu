package com.demo.jiapu.util;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mViewId;
    private int mTab;
    private int mType = 0;

    private List<String> mFragmentPaths;
    private List<Integer> mFragmentType;
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, @IdRes int viewId, List<String> fragmentPaths) {
        this.mFragmentManager = fm;
        this.mViewId = viewId;
        this.mFragmentPaths = fragmentPaths;

        initFragments();
    }

    public FragmentChangeManager(FragmentManager fm, @IdRes int viewId, List<String> fragmentPaths, int type) {
        this.mFragmentManager = fm;
        this.mViewId = viewId;
        this.mFragmentPaths = fragmentPaths;
        this.mType = type;
        initFragments();
    }

    /**
     * 初始化fragments
     */
    private void initFragments() {
        for (Fragment fragment : mFragmentManager.getFragments()) {
            hideFragment(fragment);
        }

        showFragment(getFragment(mType));
    }

    /**
     * 界面切换控制
     */
    public Fragment switchFragment(int index) {
        hideFragment(getFragment(mCurrentTab));

        Fragment fragment = getFragment(index);
        showFragment(fragment);

        mCurrentTab = index;

        return fragment;
    }

    /**
     * 获得当前fragment
     */
    public Fragment getCurrentFragment() {
        return getFragment(mCurrentTab);
    }

    public Fragment getCurrentFragment(int position) {
        String path = mFragmentPaths.get(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(path);
        if (fragment != null) {
            return fragment;
        }
        return null;
    }

    private Fragment getFragment(int position) {
        String path = mFragmentPaths.get(position);

        if (mFragmentType != null) {
            mTab = mFragmentType.get(position);
        }

        Fragment fragment = mFragmentManager.findFragmentByTag(path);
        if (fragment == null) {
//            fragment = (Fragment) ARouter.getInstance()
//                    .build(path)
//                    .withInt("tab", mTab)
//                    .navigation();

            if (fragment == null) {
                return null;
            }

            mFragmentManager.beginTransaction()
                    .add(mViewId, fragment, path)
                    .commitAllowingStateLoss();

        }

        return fragment;
    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        mFragmentManager.beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();
    }

    private void hideFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        mFragmentManager.beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }
}