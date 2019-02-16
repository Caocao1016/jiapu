package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: AppVersionResponse
 * @Description:
 */

public class OCRResponse {

        private int retcode ;
        private String retmsg ;
        private String userId ;
        private OCRData data ;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OCRData getData() {
        return data;
    }

    public void setData(OCRData data) {
        this.data = data;
    }

    public class  OCRData {

            private String issuingAuthority;
            private String address;
            private String birthdate;
            private String nation;
            private String sex;
            private String name;
            private String validity;
            private String idno;

            public String getIssuingAuthority() {
                return issuingAuthority;
            }

            public void setIssuingAuthority(String issuingAuthority) {
                this.issuingAuthority = issuingAuthority;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBirthdate() {
                return birthdate;
            }

            public void setBirthdate(String birthdate) {
                this.birthdate = birthdate;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            public String getIdno() {
                return idno;
            }

            public void setIdno(String idno) {
                this.idno = idno;
            }
        }
}
