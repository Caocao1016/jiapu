package com.demo.jiapu.db;

import android.content.Context;
import android.text.TextUtils;

import com.demo.jiapu.bean.FamilyBean;
import com.demo.jiapu.bean.MemberBean;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 家庭成员数据库帮助类
 */

public class FamilyDBHelper {

    private String dbName = "FamilyTree.db";
    private final boolean DEBUGGABLE = true; // 是否输出log

    private LiteOrm liteOrm;

    private boolean mInquirySpouse = true;//是否查询配偶

    public FamilyDBHelper(Context context, String dbname) {
        this.dbName = dbname;
        liteOrm = LiteOrm.newSingleInstance(context, dbName);
        liteOrm.setDebugged(DEBUGGABLE);
    }

    public void setInquirySpouse(boolean inquirySpouse) {
        this.mInquirySpouse = inquirySpouse;
    }

    public boolean ismInquirySpouse() {
        return mInquirySpouse;
    }

    public FamilyBean findFamilyById(String familyId) {
        if (!TextUtils.isEmpty(familyId)) {
            List<FamilyBean> families = liteOrm.query(new QueryBuilder<>(FamilyBean.class).where("memberId = ?", familyId));
            if (families.size() > 0) {
                return families.get(0);
            }
        }

        return null;
    }

    public List<FamilyBean> findChildrenByParentId(String parentId, String ignoreChildId) {
        if (!TextUtils.isEmpty(parentId)) {
            return liteOrm.query(new QueryBuilder<>(FamilyBean.class)
                    .appendOrderAscBy("birthday")
                    .where("(motherId = ? or fatherId = ?) and memberId != ?", parentId, parentId, ignoreChildId));
        } else {
            return new ArrayList<>();
        }
    }

    private List<FamilyBean> findChildrenByParentId(String parentId, String ignoreChildId, String birthday, boolean isLittle) {
        String sql;
        if (!TextUtils.isEmpty(parentId)) {
            sql = "(fatherId = ? or motherId = ?) and memberId != ?";
        } else {
            return new ArrayList<>();
        }

        if (isLittle) {
            sql += " and birthday > ?";
        } else {
            sql += " and birthday <= ?";
        }

        return liteOrm.query(new QueryBuilder<>(FamilyBean.class)
                .appendOrderAscBy("birthday")
                .where(sql, parentId, parentId, ignoreChildId, birthday));
    }

    public int deleteAll() {
        return liteOrm.deleteAll(MemberBean.class);
    }

    public int save(List<MemberBean> families) {
        return liteOrm.save(families);
    }

    public long save(MemberBean family) {
        return liteOrm.save(family);
    }

    public int updateSpouseId(String currentId, String spouseId) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put("spouseId", spouseId);
        return liteOrm.update(new WhereBuilder(FamilyBean.class)
                .where("memberId = ?", currentId), new ColumnsValue(map), ConflictAlgorithm.Fail);
    }

    public int updateParentId(String fatherId, String motherId) {
        final Map<String, Object> map = new HashMap<>(2);
        map.put("fatherId", fatherId);
        map.put("motherId", motherId);
        return liteOrm.update(new WhereBuilder(FamilyBean.class)
                .where("fatherId = ? or motherId = ?", fatherId, motherId), new ColumnsValue(map), ConflictAlgorithm.Fail);
    }

    public int exchangeParentId(String afterChangeFatherId, String afterChangeMotherId) {
        final Map<String, Object> map = new HashMap<>(2);
        map.put("fatherId", afterChangeFatherId);
        map.put("motherId", afterChangeMotherId);
        return liteOrm.update(new WhereBuilder(FamilyBean.class)
                .where("fatherId = ? or motherId = ?", afterChangeMotherId, afterChangeFatherId), new ColumnsValue(map), ConflictAlgorithm.Fail);
    }

    public int updateGender(String familyId, String gender) {
        final Map<String, Object> map = new HashMap<>(1);
        map.put("sex", gender);
        return liteOrm.update(new WhereBuilder(FamilyBean.class)
                .where("memberId = ?", familyId), new ColumnsValue(map), ConflictAlgorithm.Fail);
    }

    public void closeDB() {
        if (liteOrm != null) {
            liteOrm.close();
        }
    }

    public FamilyBean getCouple(String maleId, String femaleId) {
        final FamilyBean male = findFamilyById(maleId);
        final FamilyBean female = findFamilyById(femaleId);
        if (male != null) {
            male.setSpouse(female);
            return male;
        } else if (female != null) {
            return female;
        }
        return null;
    }

    public List<FamilyBean> getChildrenAndGrandChildren(FamilyBean parentInfo, String ignoreId) {
        final String parentId = parentInfo.getMemberId();
        final List<FamilyBean> childrenList = findChildrenByParentId(parentId, ignoreId);
        setSpouse(childrenList);
        setChildren(childrenList);

        return childrenList;
    }


    public List<FamilyBean> getMyBrothers(FamilyBean myInfo, boolean isLittle) {
        final String myId = myInfo.getMemberId();
        final String myBirthday = myInfo.getBirthday();
        final String myFatherId = myInfo.getFatherId();
        final String myMotherId = myInfo.getMotherId();
        final String parentId;
        if (!TextUtils.isEmpty(myFatherId)) {
            parentId = myFatherId;
        } else {
            parentId = myMotherId;
        }

        final List<FamilyBean> brotherList = findChildrenByParentId(parentId, myId, myBirthday, isLittle);
        setSpouse(brotherList);
        setChildren(brotherList);
        return brotherList;
    }


    private void setChildren(List<FamilyBean> familyList) {
        if (familyList != null) {
            for (FamilyBean family : familyList) {
                final String familyId = family.getMemberId();
                final List<FamilyBean> childrenList = findChildrenByParentId(familyId, "");
                if (childrenList != null && mInquirySpouse) {
                    setSpouse(childrenList);
                }
                family.setChildren(childrenList);
                setGrandChildrenType(childrenList);
            }
        }
    }

    private void setGrandChildrenType(List<FamilyBean> familyList) {
        if (familyList != null) {
            for (FamilyBean family : familyList) {
                final String familyId = family.getMemberId();
                final List<FamilyBean> childrenList = findChildrenByParentId(familyId, "");
                if (childrenList.size() > 0) {
                    family.setGrandChildrenHaveSon(true);
                    if (mInquirySpouse)
                        setSpouse(childrenList);
                }
            }
        }
    }

    private void setSpouse(List<FamilyBean> familyList) {
        for (FamilyBean family : familyList) {
            setSpouse(family);
        }
    }

    public void setSpouse(FamilyBean family) {
        final String spouseId = family.getSpouseId();
        family.setSpouse(findFamilyById(spouseId));
        if (family.getSpouse() != null)
            family.getSpouse().setMemberSpouse(true);
    }


}
