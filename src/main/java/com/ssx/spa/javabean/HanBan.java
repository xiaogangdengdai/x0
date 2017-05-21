package com.ssx.spa.javabean;

import java.io.Serializable;

public class HanBan implements Serializable {
    private String Aexpected;
    private String Airline;
    private String AirlineCode;
    private String ArrCity;
    private String ArrCode;
    private String ArrTerminal;
    private String ArrTime;
    private String DepCity;
    private String DepCode;
    private String DepTerminal;
    private String DepTime;
    private String Dexpected;
    private String FlightDate;
    private String FlightNum;
    private String OnTimeRate;
    private String PEKDate;

    public void setFlightNum(String FlightNum) {
        this.FlightNum = FlightNum;
    }

    public String getFlightNum() {
        return this.FlightNum;
    }

    public void setAirlineCode(String AirlineCode) {
        this.AirlineCode = AirlineCode;
    }

    public String getAirlineCode() {
        return this.AirlineCode;
    }

    public void setAirline(String Airline) {
        this.Airline = Airline;
    }

    public String getAirline() {
        return this.Airline;
    }

    public void setDepCity(String DepCity) {
        this.DepCity = DepCity;
    }

    public String getDepCity() {
        return this.DepCity;
    }

    public void setArrCity(String ArrCity) {
        this.ArrCity = ArrCity;
    }

    public String getArrCity() {
        return this.ArrCity;
    }

    public void setDepCode(String DepCode) {
        this.DepCode = DepCode;
    }

    public String getDepCode() {
        return this.DepCode;
    }

    public void setArrCode(String ArrCode) {
        this.ArrCode = ArrCode;
    }

    public String getArrCode() {
        return this.ArrCode;
    }

    public void setOnTimeRate(String OnTimeRate) {
        this.OnTimeRate = OnTimeRate;
    }

    public String getOnTimeRate() {
        return this.OnTimeRate;
    }

    public void setDepTerminal(String DepTerminal) {
        this.DepTerminal = DepTerminal;
    }

    public String getDepTerminal() {
        return this.DepTerminal;
    }

    public void setArrTerminal(String ArrTerminal) {
        this.ArrTerminal = ArrTerminal;
    }

    public String getArrTerminal() {
        return this.ArrTerminal;
    }

    public void setFlightDate(String FlightDate) {
        this.FlightDate = FlightDate;
    }

    public String getFlightDate() {
        return this.FlightDate;
    }

    public void setPEKDate(String PEKDate) {
        this.PEKDate = PEKDate;
    }

    public String getPEKDate() {
        return this.PEKDate;
    }

    public void setDepTime(String DepTime) {
        this.DepTime = DepTime;
    }

    public String getDepTime() {
        return this.DepTime;
    }

    public void setArrTime(String ArrTime) {
        this.ArrTime = ArrTime;
    }

    public String getArrTime() {
        return this.ArrTime;
    }

    public void setDexpected(String Dexpected) {
        this.Dexpected = Dexpected;
    }

    public String getDexpected() {
        return this.Dexpected;
    }

    public void setAexpected(String Aexpected) {
        this.Aexpected = Aexpected;
    }

    public String getAexpected() {
        return this.Aexpected;
    }
}
