package com.demo.jiapu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.jiapu.R;
import com.demo.jiapu.bean.JpsjListDataBean;

import java.util.List;

public class HomeRightAdapter extends BaseQuickAdapter<JpsjListDataBean, BaseViewHolder> {
    private Context mContext;

    public HomeRightAdapter(List<JpsjListDataBean> data) {
        super(R.layout.adapet_home_rig, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JpsjListDataBean item) {

//        TextView mHead = helper.getView(R.id.number);
        TextView mTitle = helper.getView(R.id.tv_list_title);
        TextView mNumber = helper.getView(R.id.number);
        mNumber.setText(setNumber(item.getR_num()));
        mTitle.setText(item.getTitle());
        ImageView mTop = helper.getView(R.id.iv_list_zhiding);
        mTop.setVisibility(item.getZhi_ding() == 0 ? View.GONE : View.VISIBLE);
        if (!item.getJp_img().equals(""))
            Glide.with(helper.itemView).load(item.getJp_img()).into((ImageView) helper.getView(R.id.iv_list_image));
    }


    private String setNumber(int num) {

        if (num < 10000) {
            return String.format("人数：%s", num);
        } else {
            return String.format("人数：%sw", (double) num / 10000);
        }
    }
}

