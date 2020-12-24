package com.demo.jiapu.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.jiapu.R;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.listener.OnViewGroupItemClickListener;
import com.demo.jiapu.util.UIUtils;
import com.demo.jiapu.widget.AddFamilyViewGroup;


public class MenuAddDialog extends BaseFullScreenDialog implements OnViewGroupItemClickListener {

    private FamilyBean familyBean;

    public MenuAddDialog(Context context) {
        super(context);
    }

    public MenuAddDialog(Context context, FamilyBean family) {
        super(context);
        this.familyBean = family;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.menu_add_family;
    }

    @Override
    public void init() {
        AddFamilyViewGroup viewGroup = findViewById(R.id.vg_menu_add);
        viewGroup.addItemView(familyBean);
        viewGroup.addItemView(2);
        viewGroup.addItemView(3);
        viewGroup.addItemView(4);
        viewGroup.addItemView(5);
        viewGroup.addItemView(6);
        viewGroup.addItemView(7);
        viewGroup.drawViewGroup();
        viewGroup.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(int position) {
        Log.i("TAG:", position + "");
        switch (position) {
            case AddFamilyViewGroup.ITEM_BROTHER:
                UIUtils.showToast("兄弟姐妹");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_DAUGHTER:
                UIUtils.showToast("女儿");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_FATHER:
                UIUtils.showToast("父亲");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_MOTHER:
                UIUtils.showToast("母亲");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SON:
                UIUtils.showToast("儿子");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SPOUSE:
                UIUtils.showToast("配偶");
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_MY:
                break;
            default:
                dismiss();
                break;
        }
    }
}
