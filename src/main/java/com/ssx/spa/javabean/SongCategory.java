package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class SongCategory implements Serializable {
    private String icon;
    private Integer id;
    private String name;
    private List<Song> songs;

    public SongCategory(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
