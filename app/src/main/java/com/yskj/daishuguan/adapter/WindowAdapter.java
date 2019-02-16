package com.yskj.daishuguan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.HelpResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/12/29
 *
 * @ClassName: HelpAdapter
 * @Description:
 */

public class WindowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public WindowAdapter(List<String> data) {
        super(R.layout.adapter_window, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_cancel,item);
        helper.addOnClickListener(R.id.tv_cancel);
    }

}

