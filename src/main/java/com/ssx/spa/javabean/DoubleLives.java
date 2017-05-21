package com.ssx.spa.javabean;

import java.io.Serializable;

public class DoubleLives implements Serializable {
    private int channelcode;
    private String freq;
    private int id;
    private String name;
    private int position;
    private String radiozhishi;
    private String zhishi;

    public void setChannelcode(int channelcode) {
        this.channelcode = channelcode;
    }

    public int getChannelcode() {
        return this.channelcode;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getFreq() {
        return this.freq;
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

    public void setRadiozhishi(String radiozhishi) {
        this.radiozhishi = radiozhishi;
    }

    public String getRadiozhishi() {
        return this.radiozhishi;
    }

    public void setZhishi(String zhishi) {
        this.zhishi = zhishi;
    }

    public String getZhishi() {
        return this.zhishi;
    }
}
