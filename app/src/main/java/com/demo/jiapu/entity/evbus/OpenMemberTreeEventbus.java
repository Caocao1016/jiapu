package com.demo.jiapu.entity.evbus;

import com.demo.jiapu.bean.FamilyBean;

public class OpenMemberTreeEventbus {
    private FamilyBean member;

    public OpenMemberTreeEventbus(FamilyBean member) {
        this.member = member;
    }

    public FamilyBean getMember() {
        return member;
    }

    public void setMember(FamilyBean member) {
        this.member = member;
    }
}
