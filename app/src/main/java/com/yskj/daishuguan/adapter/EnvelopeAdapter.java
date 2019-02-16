package com.yskj.daishuguan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yskj.daishuguan.R;
import com.yskj.daishuguan.response.AuthorizeResponse;

import java.util.List;

/**
 * CaoPengFei
 * 2018/11/26
 *
 * @ClassName: AuthorizationAdapter
 * @Description:
 */

public class EnvelopeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {



    public EnvelopeAdapter(List<String> data) {
        super( R.layout.adapter_envelope,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
