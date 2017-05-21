package com.ssx.spa.javabean;

import java.io.Serializable;

public class HeadSwitch implements Serializable {
    private int id;
    private String name;
    private int status;

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

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
