package com.yskj.daishuguan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.ManagementListItemResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class OverdueAdapter extends BaseQuickAdapter<ManagementListItemResponse, BaseViewHolder> {



    public OverdueAdapter(List<ManagementListItemResponse> data) {
        super( R.layout.adapter_money_overdue,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ManagementListItemResponse item) {
        helper.setText(R.id.tv_number,String.valueOf(item.getAmount()));
        helper.setText(R.id.tv_name, item.getCouponName());
        helper.setText(R.id.tv_time, item.getEndTime());
    }
}
