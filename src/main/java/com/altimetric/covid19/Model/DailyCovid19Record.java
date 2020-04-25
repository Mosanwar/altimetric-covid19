package com.altimetric.covid19.Model;

public class DailyCovid19Record {

    private String dateCollected;
    private int cdcLabs;
    private int usPubHealthLabs;
    private int dailyTotal;
    private int lag;

    //----------------------------constructors------------------------

    public DailyCovid19Record() {
    }

    public DailyCovid19Record(String dateCollected, int cdcLabs, int usPubHealthLabs, int dailyTotal, int lag) {
        this.dateCollected = dateCollected;
        this.cdcLabs = cdcLabs;
        this.usPubHealthLabs = usPubHealthLabs;
        this.dailyTotal = dailyTotal;
        this.lag = lag;
    }

    //-------------------------setters and getters---------------------

    public String getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(String dateCollected) {
        this.dateCollected = dateCollected;
    }

    public int getCdcLabs() {
        return cdcLabs;
    }

    public void setCdcLabs(int cdcLabs) {
        this.cdcLabs = cdcLabs;
    }

    public int getUsPubHealthLabs() {
        return usPubHealthLabs;
    }

    public void setUsPubHealthLabs(int usPubHealthLabs) {
        this.usPubHealthLabs = usPubHealthLabs;
    }

    public int getDailyTotal() {
        return dailyTotal;
    }

    public void setDailyTotal(int dailyTotal) {
        this.dailyTotal = dailyTotal;
    }

    public int getLag() {
        return lag;
    }

    public void setLag(int lag) {
        this.lag = lag;
    }
}
