package com.demo.jiapu.base;

/**
 * CaoPengFei
 * 2018/11/28
 *
 * @ClassName: BaseResponse
 * @Description:
 */

public class BaseResponse<T>{
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
                "returnCode=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
