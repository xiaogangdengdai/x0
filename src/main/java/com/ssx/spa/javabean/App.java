package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class App implements Serializable {
    private List<details> details;
    private int fileType;
    private int id;
    private List<Infos> infos;
    private String name;
    private String path;
    private int position;

    public void setDetails(List<details> details) {
        this.details = details;
    }

    public List<details> getDetails() {
        return this.details;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getFileType() {
        return this.fileType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setInfos(List<Infos> infos) {
        this.infos = infos;
    }

    public List<Infos> getInfos() {
        return this.infos;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
