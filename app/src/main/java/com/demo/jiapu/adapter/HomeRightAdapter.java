package com.demo.jiapu.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.jiapu.R;

import java.util.List;

public class HomeRightAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeRightAdapter(List<String> data) {
        super(R.layout.adapet_home_rig, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        TextView mHead = helper.getView(R.id.number);

        mHead.setText("人数："+item+"W");
    }
}

