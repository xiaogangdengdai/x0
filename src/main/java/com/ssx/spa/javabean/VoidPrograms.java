package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class VoidPrograms implements Serializable {
    private Ad ad;
    private String area;
    private String filmtype;
    private int id;
    private int maxVisitCount;
    private int position;
    private int types;
    private List<Videos> videos;
    private int vodpartid;
    private String voidprogramdescription;
    private String voidprogramdirector;
    private String voidprogramimg;
    private String voidprogramname;
    private String voidprogramstarring;
    private String year;

    public int getTypes() {
        return this.types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public Ad getAd() {
        return this.ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setFilmtype(String filmtype) {
        this.filmtype = filmtype;
    }

    public String getFilmtype() {
        return this.filmtype;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setMaxVisitCount(int maxVisitCount) {
        this.maxVisitCount = maxVisitCount;
    }

    public int getMaxVisitCount() {
        return this.maxVisitCount;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public List<Videos> getVideos() {
        return this.videos;
    }

    public void setVodpartid(int vodpartid) {
        this.vodpartid = vodpartid;
    }

    public int getVodpartid() {
        return this.vodpartid;
    }

    public void setVoidprogramdescription(String voidprogramdescription) {
        this.voidprogramdescription = voidprogramdescription;
    }

    public String getVoidprogramdescription() {
        return this.voidprogramdescription;
    }

    public void setVoidprogramdirector(String voidprogramdirector) {
        this.voidprogramdirector = voidprogramdirector;
    }

    public String getVoidprogramdirector() {
        return this.voidprogramdirector;
    }

    public void setVoidprogramimg(String voidprogramimg) {
        this.voidprogramimg = voidprogramimg;
    }

    public String getVoidprogramimg() {
        return this.voidprogramimg;
    }

    public void setVoidprogramname(String voidprogramname) {
        this.voidprogramname = voidprogramname;
    }

    public String getVoidprogramname() {
        return this.voidprogramname;
    }

    public void setVoidprogramstarring(String voidprogramstarring) {
        this.voidprogramstarring = voidprogramstarring;
    }

    public String getVoidprogramstarring() {
        return this.voidprogramstarring;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }
}
