package com.yskj.daishuguan.response;

/**
 * CaoPengFei
 * 2019/2/14
 *
 * @ClassName: CreateReportInfo
 * @Description:
 */

public class CreateReportInfo {
    /**
     * retcode : 1000
     * retmsg : 成功
     * userid : ED9E0DC2C41EE728D2919146BDC149D1
     * data : {"code":"105","smscode":true,"needtostart":false,"nextstage":"QUERY","complete":false,"taskid":"TASKYYS100000201803081049170711031026"}
     */

    private int retcode;
    private String retmsg;
    private String userid;
    private DataBean data;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 105
         * smscode : true
         * needtostart : false
         * nextstage : QUERY
         * complete : false
         * taskid : TASKYYS100000201803081049170711031026
         */

        private String code;
        private boolean smscode;
        private boolean needtostart;
        private String nextstage;
        private boolean complete;
        private String taskid;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isSmscode() {
            return smscode;
        }

        public void setSmscode(boolean smscode) {
            this.smscode = smscode;
        }

        public boolean isNeedtostart() {
            return needtostart;
        }

        public void setNeedtostart(boolean needtostart) {
            this.needtostart = needtostart;
        }

        public String getNextstage() {
            return nextstage;
        }

        public void setNextstage(String nextstage) {
            this.nextstage = nextstage;
        }

        public boolean isComplete() {
            return complete;
        }

        public void setComplete(boolean complete) {
            this.complete = complete;
        }

        public String getTaskid() {
            return taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }
    }
}
