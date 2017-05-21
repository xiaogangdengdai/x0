package com.ssx.spa.javabean;

import java.io.Serializable;

public class Videos implements Serializable {
    private String createadmin;
    private String createtime;
    private int id;
    private int ifenable;
    private int position;
    private int vodprogramid;
    private String voidname;
    private String voidpath;
    private int voidstatus;

    public void setCreateadmin(String createadmin) {
        this.createadmin = createadmin;
    }

    public String getCreateadmin() {
        return this.createadmin;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setIfenable(int ifenable) {
        this.ifenable = ifenable;
    }

    public int getIfenable() {
        return this.ifenable;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void setVodprogramid(int vodprogramid) {
        this.vodprogramid = vodprogramid;
    }

    public int getVodprogramid() {
        return this.vodprogramid;
    }

    public void setVoidname(String voidname) {
        this.voidname = voidname;
    }

    public String getVoidname() {
        return this.voidname;
    }

    public void setVoidpath(String voidpath) {
        this.voidpath = voidpath;
    }

    public String getVoidpath() {
        return this.voidpath;
    }

    public void setVoidstatus(int voidstatus) {
        this.voidstatus = voidstatus;
    }

    public int getVoidstatus() {
        return this.voidstatus;
    }
}
