package com.yskj.daishuguan.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.BillHuankuanResponse;
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

public class BillHuankuanAdapter extends BaseQuickAdapter<BillHuankuanResponse.ListBean, BaseViewHolder> {


    public BillHuankuanAdapter(List<BillHuankuanResponse.ListBean> data) {
        super(R.layout.adapter_bill_huan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillHuankuanResponse.ListBean item) {

//            0  代还款  3   已还款
        if (   item.getStatus() == 0) {
            if (item.getIdDued() == 1) {
                helper.setGone(R.id.rl_one, true);
                helper.setText(R.id.tv_start, "已逾期：" + item.getDuedDay() + "天");
                helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#EA1616"));
            } else {
                helper.setGone(R.id.rl_one, false);
                if (item.getIsExtension() == 1) {
                    if (item.getExtensionType() ==1 ) {
                        helper.setText(R.id.tv_start, "距离还款日：" + item.getPaymentDay() + "天");
                        helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FCB112"));
                    } else if (item.getExtensionType()==2) {
                        helper.setText(R.id.tv_start, "展期申请失败");
                        helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FCB112"));
                    } else if (item.getExtensionType()==3) {
                        helper.setText(R.id.tv_start, "展期申请中...");
                        helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#7BC076"));
                    }else if (item.getExtensionType() == 4) {
                        helper.setText(R.id.tv_start, "展期通过待确认...");
                        helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#CCCCCC"));
                    }
                } else {

                    helper.setText(R.id.tv_start, "距离还款日：" + item.getPaymentDay() + "天");
                    helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#FCB112"));
                }


            }
        } else if (item.getStatus() == 3) {
            helper.setText(R.id.tv_start, "本期已还");
            helper.setBackgroundColor(R.id.tv_start, Color.parseColor("#67BD66"));
        }
        helper.setText(R.id.tv_money, StringUtil.getValue(item.getRepayTotal()));
        helper.setText(R.id.tv_time, "放款时间   " + item.getCreateTime());
        helper.setText(R.id.tv_interest, "申请时间   " + item.getCreateTime());
        helper.setText(R.id.tv, "借款周期：" + item.getLoanDate() + "天");
        helper.addOnClickListener(R.id.rl_all);
    }
}
