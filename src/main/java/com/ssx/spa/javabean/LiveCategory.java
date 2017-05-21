package com.ssx.spa.javabean;

import java.io.Serializable;

public class LiveCategory implements Serializable {
    private int id;
    private String name;
    private String path;
    private int statusConfig;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setStatusConfig(int statusConfig) {
        this.statusConfig = statusConfig;
    }

    public int getStatusConfig() {
        return this.statusConfig;
    }
}
