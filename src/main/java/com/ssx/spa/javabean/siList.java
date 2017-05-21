package com.ssx.spa.javabean;

import java.io.Serializable;

public class siList implements Serializable {
    private Ad ad;
    private int adid;
    private String description;
    private int id;
    private String logo;
    private int lv;
    private String name;
    private int parentID;
    private int position;

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Ad getAd() {
        return this.ad;
    }

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

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getParentID() {
        return this.parentID;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
