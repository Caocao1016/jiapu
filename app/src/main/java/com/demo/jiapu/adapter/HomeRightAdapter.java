package com.demo.jiapu.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.jiapu.R;
import com.demo.jiapu.bean.JpsjListDataBean;

import java.util.List;

public class HomeRightAdapter extends BaseQuickAdapter<JpsjListDataBean, BaseViewHolder> {

    public HomeRightAdapter(List<JpsjListDataBean> data) {

        super(R.layout.adapet_home_rig, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JpsjListDataBean item) {

//        TextView mHead = helper.getView(R.id.number);
        TextView mTitle = helper.getView(R.id.tv_list_title);
        mTitle.setText(item.getTitle());
    }
}

