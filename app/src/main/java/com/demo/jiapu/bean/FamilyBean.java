package com.demo.jiapu.bean;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.List;

@Table("FamilyBean")
public class FamilyBean  {

    @PrimaryKey(AssignType.BY_MYSELF)
    private String memberId;//人员ID
    private String memberName;//姓名
    private String memberImg;//头像
    private String sex;//性别：1男，2女
    private String birthday;//生日

    private String fatherId;//父亲ID
    private String motherId;//母亲ID
    private String spouseId;//配偶ID



    @Ignore
    private FamilyBean spouse;//配偶
    @Ignore
    private List<FamilyBean> children;//儿女
    @Ignore
    private boolean isSelect = false;//是否选中
    @Ignore
    private boolean isMemberSpouse = false;//是否为直系亲属的配偶
    @Ignore
    private boolean isGrandChildrenHaveSon = false;//孙子是否有儿子



    public boolean isGrandChildrenHaveSon() {
        return isGrandChildrenHaveSon;
    }

    public void setGrandChildrenHaveSon(boolean grandChildrenHaveSon) {
        isGrandChildrenHaveSon = grandChildrenHaveSon;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }


    public FamilyBean getSpouse() {
        return spouse;
    }

    public void setSpouse(FamilyBean spouse) {
        this.spouse = spouse;
    }

    public List<FamilyBean> getChildren() {
        return children;
    }

    public void setChildren(List<FamilyBean> children) {
        this.children = children;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    public boolean isMemberSpouse() {
        return isMemberSpouse;
    }

    public void setMemberSpouse(boolean memberSpouse) {
        isMemberSpouse = memberSpouse;
    }
}
