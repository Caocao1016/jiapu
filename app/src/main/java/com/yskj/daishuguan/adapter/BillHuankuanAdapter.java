package com.yskj.daishuguan.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.BillHuankuanResponse;
import com.yskj.daishuguan.response.BillResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class BillHuankuanAdapter extends BaseQuickAdapter<BillHuankuanResponse.ListBean, BaseViewHolder> {



    public BillHuankuanAdapter(List<BillHuankuanResponse.ListBean> data) {
        super( R.layout.adapter_bill_huan,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillHuankuanResponse.ListBean item) {
//            0  审核中  3   未通过

         if (item.getStatus() == 0){
            helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FCB112"));
         }else if (item.getStatus() == 3){
             helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#9A9A9A"));
         }else {
             helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#67BD66"));
         }


//        helper.setText(R.id.tv_money,item.getAuditCreditLimit()+"");
//        helper.setText(R.id.tv_start,item.getStatusString()+"");
        helper.setText(R.id.tv_time,"放款时间   "+item.getFinishTime());
        helper.setText(R.id.tv_interest,"申请时间   "+item.getCreateTime());
//        helper.setText(R.id.tv,"借款周期"+);
        helper.addOnClickListener(R.id.rl_all);


    }
}
