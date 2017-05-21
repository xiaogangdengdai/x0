package com.ssx.spa.javabean;

import java.io.Serializable;

public class FodCategory implements Serializable {
    private int id;
    private String img;
    private String name;

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
}
