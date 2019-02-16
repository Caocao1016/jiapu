package com.yskj.daishuguan.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * CaoPengFei
 * 2018/11/22
 *
 * @ClassName: SpaceItemDecoration
 * @Description:
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) == 0) {
            outRect.top = space;
        }
    }
}