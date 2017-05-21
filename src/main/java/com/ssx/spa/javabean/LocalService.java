package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class LocalService implements Serializable {
    private int adid;
    private String description;
    private int id;
    private String logo;
    private int lv;
    private String name;
    private int position;
    private int shigongcanba;
    private List<siList> siList;

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public int getAdid() {
        return this.adid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getLv() {
        return this.lv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void setShigongcanba(int shigongcanba) {
        this.shigongcanba = shigongcanba;
    }

    public int getShigongcanba() {
        return this.shigongcanba;
    }

    public void setSiList(List<siList> siList) {
        this.siList = siList;
    }

    public List<siList> getSiList() {
        return this.siList;
    }
}
