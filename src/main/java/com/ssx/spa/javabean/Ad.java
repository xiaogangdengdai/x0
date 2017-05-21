package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class Ad implements Serializable {
    private int category;
    private int contentType;
    private List<AdDetail> details;
    private int id;
    private String name;
    private int position;

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return this.category;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getContentType() {
        return this.contentType;
    }

    public void setDetails(List<AdDetail> details) {
        this.details = details;
    }

    public List<AdDetail> getDetails() {
        return this.details;
    }

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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
