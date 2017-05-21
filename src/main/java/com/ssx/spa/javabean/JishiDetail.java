package com.ssx.spa.javabean;

import java.io.Serializable;

public class JishiDetail implements Serializable {
    private int age;
    private int heigh;
    private int id;
    private String img;
    private JiShiLevel level;
    private int levelId;
    private String name;
    private String nativePlace;
    private int number;
    private int onDuty;
    private int sex;
    private int status;
    private JishiType type;
    private int typeId;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public int getHeigh() {
        return this.heigh;
    }

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

    public void setLevel(JiShiLevel level) {
        this.level = level;
    }

    public JiShiLevel getLevel() {
        return this.level;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelId() {
        return this.levelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() {
        return this.nativePlace;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setOnDuty(int onDuty) {
        this.onDuty = onDuty;
    }

    public int getOnDuty() {
        return this.onDuty;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return this.sex;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setType(JishiType type) {
        this.type = type;
    }

    public JishiType getType() {
        return this.type;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return this.typeId;
    }
}
