package com.demo.jiapu.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.List;

@Table("FamilyBean")
public class FamilyBean {


    @PrimaryKey(AssignType.BY_MYSELF)
    @JSONField(name = "id")
    private String memberId;//人员ID
    @JSONField(name = "avatar")
    private String memberImg;//头像
    @JSONField(name = "father_id")
    private String fatherId;//父亲ID
    @JSONField(name = "mother_id")
    private String motherId;//母亲ID
    @JSONField(name = "spouse_id")
    private String spouseId;//配偶ID
    @JSONField(name = "birthdate")
    private String birthday;//生日

    private String sex;//性别：1男，2女

    private int tx_userid;
    private int user_id;
    private String surname;//姓
    private String names;//名
    private String seniority;//字辈
    private int sort;//排行
    private String phone;
    private int die_status; //1在世 2去世
    private String dietime;//死亡时间
    private String burial_site;//埋葬地点
    private int is_have;//1没有 2有
    private String native_place;//籍贯
    private String nickname;//微信名

    @Ignore
    private String create_time;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getTx_userid() {
        return tx_userid;
    }

    public void setTx_userid(int tx_userid) {
        this.tx_userid = tx_userid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDie_status() {
        return die_status;
    }

    public void setDie_status(int die_status) {
        this.die_status = die_status;
    }

    public String getDietime() {
        return dietime;
    }

    public void setDietime(String dietime) {
        this.dietime = dietime;
    }

    public String getBurial_site() {
        return burial_site;
    }

    public void setBurial_site(String burial_site) {
        this.burial_site = burial_site;
    }

    public int getIs_have() {
        return is_have;
    }

    public void setIs_have(int is_have) {
        this.is_have = is_have;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
