package com.demo.jiapu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class BillAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BillAdapter(List<String> data) {
        super(0, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
