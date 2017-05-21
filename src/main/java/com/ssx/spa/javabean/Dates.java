package com.ssx.spa.javabean;

import java.io.Serializable;

public class Dates implements Serializable {
    private String minAndHour;
    private String weekMessage;
    private String yearAndDate;

    public void setMinAndHour(String minAndHour) {
        this.minAndHour = minAndHour;
    }

    public String getMinAndHour() {
        return this.minAndHour;
    }

    public void setWeekMessage(String weekMessage) {
        this.weekMessage = weekMessage;
    }

    public String getWeekMessage() {
        return this.weekMessage;
    }

    public void setYearAndDate(String yearAndDate) {
        this.yearAndDate = yearAndDate;
    }

    public String getYearAndDate() {
        return this.yearAndDate;
    }
}
