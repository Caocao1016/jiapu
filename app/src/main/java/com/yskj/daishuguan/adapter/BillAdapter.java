package com.yskj.daishuguan.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.bean.PhoneBean;
import com.yskj.daishuguan.response.AuthorizeResponse;
import com.yskj.daishuguan.response.BillResponse;
import com.yskj.daishuguan.util.StringUtil;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class BillAdapter extends BaseQuickAdapter<BillResponse.ListBean, BaseViewHolder> {



    public BillAdapter(List<BillResponse.ListBean> data) {
        super( R.layout.adapter_bill,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillResponse.ListBean item) {
//            0  审核中  3   未通过


        if ( !StringUtil.isEmpty(item.getFlag()) && item.getFlag() .equals("1") ){
            helper.setText(R.id.tv_start,"审核已通过，次日放款");
            helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FF7B47"));
        }else {
            if (item.getStatus() == 0){
                helper.setText(R.id.tv_money,item.getCustomerCreditLimit() == 0 ? "0":item.getCustomerCreditLimit()+"");
                helper.setText(R.id.tv_start,item.getStatusString()+"");
                helper.setText(R.id.tv_name,"申请金额");
                helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FCB112"));
            }else if (item.getStatus() == 2){
                helper.setText(R.id.tv_name,"申请金额");
                helper.setText(R.id.tv_money,item.getCustomerCreditLimit() == 0 ? "0":item.getCustomerCreditLimit()+"");
                helper.setText(R.id.tv_start,item.getStatusString()+"");
                helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#9A9A9A"));
            }else if (item.getStatus() == 1){
                helper.setText(R.id.tv_name,"授信金额");
                helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#67BD66"));

                helper.setText(R.id.tv_money,item.getAuditCreditLimit() == 0 ? "0":item.getAuditCreditLimit()+"");
                if (item.isReloan() && item.isReloanMenber()){
                    helper.setText(R.id.tv_start,"待提现");
                }else if (!item.isReloan() && item.getIsMember() == 1) {
                    helper.setText(R.id.tv_start,"待提现");
                }else {
                    helper.setText(R.id.tv_start,item.getStatusString()+"");

                }
            }

        }



//        helper.setText(R.id.tv_interest,"利息："+(StringUtil.isEmpty(item.getInterestRate()) ? "**元":item.getInterestRate()+"元"));

//        helper.setText(R.id.tv,"借款周期："+item.getLoanDate()+"天");
        helper.setText(R.id.tv_time,"申请时间   "+item.getCreateTime());
        helper.addOnClickListener(R.id.rl_all);


    }
}
