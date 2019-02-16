package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2019/2/13
 *
 * @ClassName: CertificationResponse
 * @Description:
 */

public class CertificationResponse {


    private List<CertificationListResponse> itemlist;

    public List<CertificationListResponse> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<CertificationListResponse> itemlist) {
        this.itemlist = itemlist;
    }

    public class CertificationListResponse {

        private String itemcode;
        private String tips;
        private String desc;
        private int status;

        public String getItemcode() {
            return itemcode;
        }

        public void setItemcode(String itemcode) {
            this.itemcode = itemcode;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
