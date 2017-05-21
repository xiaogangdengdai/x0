package com.ssx.spa.util;

import android.graphics.drawable.Drawable;

public class AndroidAppInfo {
    private String appName;
    private Integer flags;
    private Drawable icon;
    private String packageName;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getFlags() {
        return this.flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }
}
