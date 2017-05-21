package com.ssx.spa.javabean;

public class JsonBean<T> {
    private T data;
    private String errMsg;
    private int errNum;
    private T msgBody;
    private String msgCode;
    private T retData;

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public T getMsgBody() {
        return this.msgBody;
    }

    public void setMsgBody(T msgBody) {
        this.msgBody = msgBody;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getRetData() {
        return this.retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public int getErrNum() {
        return this.errNum;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
