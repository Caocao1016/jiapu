package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;

import com.demo.jiapu.R;
import com.demo.jiapu.activity.AddFamilyActivity;
import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.listener.OnViewGroupItemClickListener;
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
        if (familyBean.getSpouseId().equals(""))
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_SPOUSE);
        if (familyBean.getFatherId().equals(""))
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_FATHER);
        if (familyBean.getMotherId().equals(""))
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_MOTHER);

        viewGroup.addItemView(AddFamilyViewGroup.ITEM_BROTHER);
        viewGroup.addItemView(AddFamilyViewGroup.ITEM_DAUGHTER);
        viewGroup.addItemView(AddFamilyViewGroup.ITEM_SON);
        viewGroup.drawViewGroup();
        viewGroup.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(int position) {
        switch (position) {
            case AddFamilyViewGroup.ITEM_BROTHER:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_DAUGHTER:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_FATHER:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_MOTHER:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SON:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SPOUSE:
                getContext().startActivity(new Intent(getContext(), AddFamilyActivity.class));
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
