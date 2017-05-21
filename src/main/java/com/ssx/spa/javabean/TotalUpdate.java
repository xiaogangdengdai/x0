package com.ssx.spa.javabean;

import java.io.Serializable;

public class TotalUpdate implements Serializable {
    private String apkUrl;
    private boolean update;

    public String getApkUrl() {
        return this.apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean getUpdate() {
        return this.update;
    }
}
