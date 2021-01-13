package com.demo.jiapu.bean;

import java.util.ArrayList;
import java.util.List;

public class TreeBean {
    private FamilyBean my;
    private List<FamilyBean> myChild=new ArrayList<>();
    private FamilyBean myParent;
    private FamilyBean myPGrandParent;
    private FamilyBean myMGrandParent;
    private List<FamilyBean> myFaUncle = new ArrayList<>();
    private List<FamilyBean> myMaUncle = new ArrayList<>();
    private List<FamilyBean> myBrother = new ArrayList<>();
    private List<FamilyBean> myLittleBrother = new ArrayList<>();


    public FamilyBean getMy() {
        return my;
    }

    public void setMy(FamilyBean my) {
        this.my = my;
    }

    public List<FamilyBean> getMyChild() {
        return myChild;
    }

    public void setMyChild(List<FamilyBean> myChild) {
        this.myChild.addAll(myChild);
    }

    public FamilyBean getMyParent() {
        return myParent;
    }

    public void setMyParent(FamilyBean myParent) {
        this.myParent = myParent;
    }

    public FamilyBean getMyPGrandParent() {
        return myPGrandParent;
    }

    public void setMyPGrandParent(FamilyBean myPGrandParent) {
        this.myPGrandParent = myPGrandParent;
    }

    public List<FamilyBean> getMyFaUncle() {
        return myFaUncle;
    }

    public void setMyFaUncle(List<FamilyBean> myFaUncle) {
        this.myFaUncle.addAll(myFaUncle);
    }

    public List<FamilyBean> getMyMaUncle() {
        return myMaUncle;
    }

    public void setMyMaUncle(List<FamilyBean> myMaUncle) {
        this.myMaUncle.addAll(myMaUncle);
    }

    public List<FamilyBean> getMyBrother() {
        return myBrother;
    }

    public void setMyBrother(List<FamilyBean> myBrother) {
        this.myBrother.addAll(myBrother);
    }

    public List<FamilyBean> getMyLittleBrother() {
        return myLittleBrother;
    }

    public void setMyLittleBrother(List<FamilyBean> myLittleBrother) {
        this.myLittleBrother.addAll(myLittleBrother);
    }

    public FamilyBean getMyMGrandParent() {
        return myMGrandParent;
    }

    public void setMyMGrandParent(FamilyBean myMGrandParent) {
        this.myMGrandParent = myMGrandParent;
    }
}
