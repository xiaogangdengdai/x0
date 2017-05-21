package com.ssx.spa.view.wearther;

import java.io.Serializable;

public class Bean implements Serializable {
    private String WD;
    private String WS;
    private String altitude;
    private String city;
    private String citycode;
    private String date;
    private String h_tmp;
    private String l_tmp;
    private double latitude;
    private double longitude;
    private String pinyin;
    private String postCode;
    private String sunrise;
    private String sunset;
    private String temp;
    private String time;
    private String weather;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCitycode() {
        return this.citycode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getAltitude() {
        return this.altitude;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return this.temp;
    }

    public void setL_tmp(String l_tmp) {
        this.l_tmp = l_tmp;
    }

    public String getL_tmp() {
        return this.l_tmp;
    }

    public void setH_tmp(String h_tmp) {
        this.h_tmp = h_tmp;
    }

    public String getH_tmp() {
        return this.h_tmp;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getWD() {
        return this.WD;
    }

    public void setWS(String WS) {
        this.WS = WS;
    }

    public String getWS() {
        return this.WS;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunrise() {
        return this.sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunset() {
        return this.sunset;
    }
}
