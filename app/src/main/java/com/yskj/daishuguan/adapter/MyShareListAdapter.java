package com.yskj.daishuguan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.ManagementListItemResponse;
import com.yskj.daishuguan.response.ShareListResponse;
import com.yskj.daishuguan.util.StringUtil;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: UesrAdapter
 * @Description:
 */

public class MyShareListAdapter extends BaseQuickAdapter<ShareListResponse.ListBean, BaseViewHolder> {



    public MyShareListAdapter(List<ShareListResponse.ListBean> data) {
        super( R.layout.adapter_share_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareListResponse.ListBean item) {
        helper.setText(R.id.tv_pepole, StringUtil.getString(item.getLoginName()));

        if (item.getInviteStatus() == 0){
            helper.setText(R.id.tv_start,"注册");
        }else  if (item.getInviteStatus() == 1){
            helper.setText(R.id.tv_start,"已借款");
        }

        helper.setText(R.id.tv_time, item.getRegTimeStr());


    }
}
