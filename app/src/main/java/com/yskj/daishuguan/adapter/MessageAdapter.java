package com.yskj.daishuguan.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.BillResponse;
import com.yskj.daishuguan.response.MessageResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class MessageAdapter extends BaseQuickAdapter<MessageResponse, BaseViewHolder> {



    public MessageAdapter(List<MessageResponse> data) {
        super( R.layout.adapter_meg,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse item) {

            helper.setText(R.id.tv_title,item.getMegTitle());
            helper.setText(R.id.tv_time,item.getTime());
            helper.setText(R.id.tv_name,item.getMegName());

    }
}
