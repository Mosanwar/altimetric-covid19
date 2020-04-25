package com.altimetric.covid19.Model;

public class MonthlyAvg {
    private String month;
    private Double avg;

    //----------------------------constructors------------------------

    public MonthlyAvg() {
    }

    public MonthlyAvg(String month, Double avg) {
        this.month = month;
        this.avg = avg;
    }

    //-------------------------setters and getters---------------------

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}
