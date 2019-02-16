package com.yskj.daishuguan.adapter;

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

        helper.setText(R.id.tv_money,item.getAuditCreditLimit()+"");
        helper.setText(R.id.tv_start,item.getStatusString());
        helper.setText(R.id.tv,item.getStatusString());
        helper.addOnClickListener(R.id.rl_all);
    }
}
