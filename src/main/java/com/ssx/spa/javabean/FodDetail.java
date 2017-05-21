package com.ssx.spa.javabean;

import java.io.Serializable;

public class FodDetail implements Serializable {
    private String description;
    private int id;
    private String img;
    private String name;
    private int styleId;

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

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getStyleId() {
        return this.styleId;
    }
}
