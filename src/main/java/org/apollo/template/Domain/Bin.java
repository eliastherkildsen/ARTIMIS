package org.apollo.template.Domain;

import java.util.Date;

public class Bin {

    private int binID,MaxCapacity;
    private Integer ResturentID;
    private Date instalationDate;

    public Bin(int binID, int resturentID, int maxCapacity, java.sql.Date instalationDate) {
        this.binID = binID;
        ResturentID = resturentID;
        MaxCapacity = maxCapacity;
        this.instalationDate = instalationDate;
    }

    public Bin(int binID, Integer resturentID, int maxCapacity, java.sql.Date instalationDate) {
        this.binID = binID;
        ResturentID = resturentID;
        MaxCapacity = maxCapacity;
        this.instalationDate = instalationDate;
    }

    public Bin(int resturentID, int maxCapacity, Date instalationDate) {
        this.binID = binID;
        ResturentID = resturentID;
        MaxCapacity = maxCapacity;
        this.instalationDate = instalationDate;
    }

    public int getBinID() {
        return binID;
    }

    public void setBinID(int binID) {
        this.binID = binID;
    }

    public Integer getResturentID() {
        return ResturentID;
    }

    public void setResturentID(Integer resturentID) {
        ResturentID = resturentID;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        MaxCapacity = maxCapacity;
    }

    public Date getInstalationDate() {
        return instalationDate;
    }

    public void setInstalationDate(java.sql.Date instalationDate) {
        this.instalationDate = instalationDate;
    }

    @Override
    public String toString() {
        return "Bin{" +
                "binID=" + binID +
                ", MaxCapacity=" + MaxCapacity +
                '}';
    }
}
