package com.demo.jiapu.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class JpsjListDataBean implements Parcelable {

    private long id ;
    private String title ;
    private String jp_img ;
    private int zhi_ding ;
    private int r_num;

    protected JpsjListDataBean(Parcel in) {
        id = in.readLong();
        title = in.readString();
        jp_img = in.readString();
        zhi_ding = in.readInt();
        r_num = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(jp_img);
        dest.writeInt(zhi_ding);
        dest.writeInt(r_num);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JpsjListDataBean> CREATOR = new Creator<JpsjListDataBean>() {
        @Override
        public JpsjListDataBean createFromParcel(Parcel in) {
            return new JpsjListDataBean(in);
        }

        @Override
        public JpsjListDataBean[] newArray(int size) {
            return new JpsjListDataBean[size];
        }
    };

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public int getZhi_ding() {
        return zhi_ding;
    }

    public void setZhi_ding(int zhi_ding) {
        this.zhi_ding = zhi_ding;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJp_img() {
        return jp_img;
    }

    public void setJp_img(String jp_img) {
        this.jp_img = jp_img;
    }
}
