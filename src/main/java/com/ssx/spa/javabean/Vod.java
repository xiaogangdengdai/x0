package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class Vod implements Serializable {
    private int id;
    private String img;
    private String partintr;
    private String vodpartname;
    private List<VoidPrograms> voidPrograms;

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

    public void setPartintr(String partintr) {
        this.partintr = partintr;
    }

    public String getPartintr() {
        return this.partintr;
    }

    public void setVodpartname(String vodpartname) {
        this.vodpartname = vodpartname;
    }

    public String getVodpartname() {
        return this.vodpartname;
    }

    public void setVoidPrograms(List<VoidPrograms> voidPrograms) {
        this.voidPrograms = voidPrograms;
    }

    public List<VoidPrograms> getVoidPrograms() {
        return this.voidPrograms;
    }
}
