package com.demo.jiapu.base;

/**
 * CaoPengFei
 * 2018/11/28
 *
 * @ClassName: BaseResponse
 * @Description:
 */

public class BaseResponse<T>{
    private int retcode;
    private String retmsg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "returnCode=" + retcode +
                ", msg='" + retmsg + '\'' +
                ", data=" + data +
                '}';
    }
}
