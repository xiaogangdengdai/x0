package com.ssx.spa.javabean;

import java.io.Serializable;

public class SingelLives implements Serializable {
    private String description;
    private String filmname;
    private int id;
    private String ifable;
    private String livemanagementaddress;
    private String livemanagementgrade;
    private String livemanagementname;
    private int position;
    private String recording;
    private String source;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setFilmname(String filmname) {
        this.filmname = filmname;
    }

    public String getFilmname() {
        return this.filmname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setIfable(String ifable) {
        this.ifable = ifable;
    }

    public String getIfable() {
        return this.ifable;
    }

    public void setLivemanagementaddress(String livemanagementaddress) {
        this.livemanagementaddress = livemanagementaddress;
    }

    public String getLivemanagementaddress() {
        return this.livemanagementaddress;
    }

    public void setLivemanagementgrade(String livemanagementgrade) {
        this.livemanagementgrade = livemanagementgrade;
    }

    public String getLivemanagementgrade() {
        return this.livemanagementgrade;
    }

    public void setLivemanagementname(String livemanagementname) {
        this.livemanagementname = livemanagementname;
    }

    public String getLivemanagementname() {
        return this.livemanagementname;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public String getRecording() {
        return this.recording;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }
}
