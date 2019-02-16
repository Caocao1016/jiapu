package com.yskj.daishuguan.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yskj.daishuguan.R;
import com.yskj.daishuguan.base.BaseActivity;
import com.yskj.daishuguan.base.BasePresenter;
import com.yskj.daishuguan.fragment.BillLeftFragment;
import com.yskj.daishuguan.fragment.BillRightFragment;
import com.yskj.daishuguan.fragment.MessageLeftFragment;
import com.yskj.daishuguan.fragment.MessageRightFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CaoPengFei
 * 2019/2/16
 *
 * @ClassName: MessageActivity
 * @Description: 消息
 */

public class MessageActivity extends BaseActivity {


    @BindView(R.id.rb_left)
    RadioButton rb_left;
    @BindView(R.id.rb_right)
    RadioButton rb_right;
    @BindView(R.id.rgTools)
    RadioGroup rg_all;
    @BindView(R.id.fl_all)
    FrameLayout fl_all;


    private Fragment[] mFragments;
    private int mIndex;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        MessageLeftFragment liftFragment = new MessageLeftFragment();
        MessageRightFragment rightFragment = new MessageRightFragment();

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
    }

    @Override
    protected void initData() {

    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_all, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @OnClick({R.id.rb_left, R.id.rb_right,R.id.iv_back})
    public void onClick(View view) {
        if (view.getId() == R.id.rb_left) {
            rb_left.setBackgroundResource(R.drawable.shape_bg_bill);
            rb_right.setBackgroundResource(R.drawable.shape_bill_bg);
            setIndexSelected(0);
        } else if (view.getId() == R.id.rb_right) {
            rb_right.setBackgroundResource(R.drawable.shape_bg_bill_right);
            rb_left.setBackgroundResource(R.drawable.shape_bill_bg_right);
            setIndexSelected(1);
        }else if(view.getId() == R.id.iv_back){
            finish();
        }
    }
    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}
