package com.demo.jiapu.db;

import android.content.Context;

import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.bean.TreeBean;

import java.util.List;

public class FamilyDbManger {
    private FamilyDBHelper dbHelper;

    private boolean mInquirySpouse = true;//是否查询配偶


    public FamilyDbManger(Context context, String dbName) {
        dbHelper = new FamilyDBHelper(context, dbName);
    }

    public FamilyBean getFamilyById(String id) {
        return dbHelper.findFamilyById(id);
    }
    public FamilyBean getFamilyByUserId(String id) {
        return dbHelper.findFamilyByUserId(id);
    }



    public TreeBean getTreeData(FamilyBean my) {
        TreeBean treeBean = new TreeBean();
        treeBean.setMy(my);
        if (my != null) {
            dbHelper.setSpouse(my);
            my.setSelect(true);
            my.setGrandChildrenHaveSon(false);
            my.setMemberSpouse(false);
            treeBean.setMyChild(dbHelper.getChildrenAndGrandChildren(my, ""));

            final String fatherId = my.getFatherId();
            final String motherId = my.getMotherId();
            treeBean.setMyParent(dbHelper.getCouple(fatherId, motherId));
            if (treeBean.getMyParent() != null) {
                final FamilyBean father;
                final FamilyBean mother;
                if ("1".equals(treeBean.getMyParent().getSex())) {
                    father = treeBean.getMyParent();
                    mother = treeBean.getMyParent().getSpouse();
                } else {
                    father = treeBean.getMyParent().getSpouse();
                    mother = treeBean.getMyParent();
                }

                if (father != null) {
                    final String pGrandFatherId = father.getFatherId();
                    final String pGrandMotherId = father.getMotherId();
                    treeBean.setMyPGrandParent(dbHelper.getCouple(pGrandFatherId, pGrandMotherId));
                    if (treeBean.getMyPGrandParent() != null) {
                        treeBean.setMyFaUncle(dbHelper.getChildrenAndGrandChildren(treeBean.getMyPGrandParent(), fatherId));
                    }
                }
                if (mother != null) {
                    final String mGrandFatherId = mother.getFatherId();
                    final String mGrandMotherId = mother.getMotherId();
                    treeBean.setMyMGrandParent(dbHelper.getCouple(mGrandFatherId, mGrandMotherId));
                    if (treeBean.getMyMGrandParent() != null) {
                        treeBean.setMyMaUncle(dbHelper.getChildrenAndGrandChildren(treeBean.getMyMGrandParent(), motherId));
                    }
                }
            }
            treeBean.setMyBrother(dbHelper.getMyBrothers(my, false));
            treeBean.setMyLittleBrother(dbHelper.getMyBrothers(my, true));
        }
        return treeBean;
    }

    public void closeDb() {
        if (null != dbHelper)
            dbHelper.closeDB();
    }

    public void save(List<FamilyBean> list) {
        dbHelper.save(list);
    }

    public void setInquirySpouse(boolean spouse) {
        dbHelper.setInquirySpouse(spouse);
    }

    public int deleteAll() {
        return dbHelper.deleteAll();
    }
}
