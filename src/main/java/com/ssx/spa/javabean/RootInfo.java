package com.ssx.spa.javabean;

import java.io.Serializable;

public class RootInfo implements Serializable {
    private String calling;
    private int categoryId;
    private String categoryName;
    private String connectTime;
    private String createDate;
    private String curDate;
    private int id;
    private String ipaddr;
    private String mac;
    private String position;
    private int runStatus;
    private int status;
    private String userName;

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    public String getConnectTime() {
        return this.connectTime;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getCurDate() {
        return this.curDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getIpaddr() {
        return this.ipaddr;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return this.mac;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getRunStatus() {
        return this.runStatus;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getCalling() {
        return this.calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }
}
