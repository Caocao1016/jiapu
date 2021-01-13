package com.demo.jiapu.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.demo.jiapu.R;
import com.demo.jiapu.base.BaseActivity;
import com.demo.jiapu.base.BasePresenter;
import com.demo.jiapu.fragment.HomeLeftFragment;
import com.demo.jiapu.fragment.HomeRightFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewHomeActivity extends BaseActivity {

    @BindView(R.id.rb_left)
    RadioButton rb_left;
    @BindView(R.id.rb_right)
    RadioButton rb_right;
    @BindView(R.id.rgTools)
    RadioGroup rg_all;
    @BindView(R.id.fl_all)
    FrameLayout fl_all;
    @BindView(R.id.tv_left)
    TextView mLeft;
    @BindView(R.id.tv_rig)
    TextView mRig;

    private Fragment[] mFragments;
    private int mIndex;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_home;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {

        HomeLeftFragment liftFragment = new HomeLeftFragment();
        HomeRightFragment rightFragment = new HomeRightFragment();

        //添加到数组
        //添加到数组
        mFragments = new Fragment[]{liftFragment, rightFragment};

        //开启事务
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.fl_all, liftFragment).commit();

        //默认设置为第0个
        setIndexSelected(0);
        mLeft.setBackgroundResource(R.drawable.icon_page_tab);
        mRig.setBackgroundColor(Color.WHITE);
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        ft.setMaxLifecycle(mFragments[mIndex], Lifecycle.State.RESUMED);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_all, mFragments[index]).show(mFragments[index]);
            ft.setMaxLifecycle(mFragments[index], Lifecycle.State.STARTED);

        } else {
            ft.setMaxLifecycle(mFragments[index], Lifecycle.State.STARTED);
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @OnClick({R.id.rb_left, R.id.rb_right, R.id.iv})
    public void onClick(View view) {
        if (view.getId() == R.id.rb_left) {
            setIndexSelected(0);
            mLeft.setBackgroundResource(R.drawable.icon_page_tab);
            mRig.setBackgroundColor(Color.WHITE);
        } else if (view.getId() == R.id.rb_right) {
            mRig.setBackgroundResource(R.drawable.icon_page_tab);
            mLeft.setBackgroundColor(Color.WHITE);
            setIndexSelected(1);
        } else if (view.getId() == R.id.iv) {
            finish();
        }
    }

    @Override
    protected void initData() {

    }
}
