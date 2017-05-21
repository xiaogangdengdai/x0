package com.ssx.spa.javabean;

import java.io.Serializable;

public class Update implements Serializable {
    private String createTime;
    private String endTime;
    private int id;
    private String name;
    private String remark;
    private Soft soft;
    private String startTime;
    private int status;
    private String targetVersion;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setSoft(Soft soft) {
        this.soft = soft;
    }

    public Soft getSoft() {
        return this.soft;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public String getTargetVersion() {
        return this.targetVersion;
    }
}
