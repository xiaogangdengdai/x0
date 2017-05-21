package com.ssx.spa.javabean;

import java.io.Serializable;

public class JiShiLevel implements Serializable {
    private int id;
    private String name;

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
}
