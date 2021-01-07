package com.demo.jiapu.dialog;

import android.content.Context;
import android.content.Intent;

import com.demo.jiapu.R;
import com.demo.jiapu.activity.AddMemberActivity;
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
        if (!familyBean.getFatherId().equals("") && !familyBean.getMotherId().equals("")) {
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_BROTHER);
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_DAUGHTER);
            viewGroup.addItemView(AddFamilyViewGroup.ITEM_SON);
        }

        viewGroup.drawViewGroup();
        viewGroup.setOnItemClickListener(this);

        findViewById(R.id.vg_menu_add).setOnClickListener(v -> {
            dismiss();
        });

    }

    @Override
    public void onItemClick(int position) {
        final Intent intent = new Intent(getContext(), AddMemberActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("bean", familyBean);

        switch (position) {
            case AddFamilyViewGroup.ITEM_BROTHER:
                intent.putExtra("itemName", "兄弟");
                intent.putExtra("itemID", "4");
                getContext().startActivity(intent);
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SPOUSE:
                intent.putExtra("itemName", "配偶");
                intent.putExtra("itemID", "3");
                getContext().startActivity(intent);
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_MOTHER:
                intent.putExtra("itemName", "妈妈");
                intent.putExtra("itemID", "2");
                getContext().startActivity(intent);
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_FATHER:
                intent.putExtra("itemName", "爸爸");
                intent.putExtra("itemID", "1");
                getContext().startActivity(intent);
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_SON:
                intent.putExtra("itemName", "儿子");
                intent.putExtra("itemID", "5");
                getContext().startActivity(intent);
                dismiss();
                break;
            case AddFamilyViewGroup.ITEM_DAUGHTER:
                intent.putExtra("itemName", "女儿");
                intent.putExtra("itemID", "5");
                getContext().startActivity(intent);
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
