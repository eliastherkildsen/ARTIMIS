package org.apollo.template.Domain;

import java.util.Date;

public class Bin {

    private int binID, ResturentID, MaxCapacity;
    private Date instalationDate;

    public Bin(int binID, int resturentID, int maxCapacity, Date instalationDate) {
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

    public int getResturentID() {
        return ResturentID;
    }

    public void setResturentID(int resturentID) {
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

    public void setInstalationDate(Date instalationDate) {
        this.instalationDate = instalationDate;
    }
}
