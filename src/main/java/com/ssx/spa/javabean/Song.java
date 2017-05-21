package com.ssx.spa.javabean;

import java.io.Serializable;

public class Song implements Serializable {
    private Integer categoryId;
    private Integer id;
    private String lyricFile;
    private String musicFile;
    private Integer playTimes;
    private Integer recommend;
    private String singer;
    private String singerType;
    private String songName;
    private String songYinpin;
    private Integer type;
    private Integer year;

    public Song(String songName, String singer, String singerType, String songYinpin, Integer recommend) {
        this.songName = songName;
        this.singer = singer;
        this.singerType = singerType;
        this.songYinpin = songYinpin;
        this.recommend = recommend;
    }

    public Song(String songName, String singer, String singerType, String songYinpin, String lyricFile, Integer year, Integer recommend) {
        this.songName = songName;
        this.singer = singer;
        this.singerType = singerType;
        this.songYinpin = songYinpin;
        this.lyricFile = lyricFile;
        this.year = year;
        this.recommend = recommend;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSingerType() {
        return this.singerType;
    }

    public void setSingerType(String singerType) {
        this.singerType = singerType;
    }

    public String getSongYinpin() {
        return this.songYinpin;
    }

    public void setSongYinpin(String songYinpin) {
        this.songYinpin = songYinpin;
    }

    public String getLyricFile() {
        return this.lyricFile;
    }

    public void setLyricFile(String lyricFile) {
        this.lyricFile = lyricFile;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRecommend() {
        return this.recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getMusicFile() {
        return this.musicFile;
    }

    public void setMusicFile(String musicFile) {
        this.musicFile = musicFile;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlayTimes() {
        return this.playTimes;
    }

    public void setPlayTimes(Integer playTimes) {
        this.playTimes = playTimes;
    }
}
