package org.apollo.template.Domain;

import org.apollo.template.Domain.Bin;

import java.util.List;

public class Resturent {

    // Restaurant object
    private String resturentName, resturentAdress;
    private int resturentID, resturentZip;

    public Resturent(int resturentID ,String resturentName, String resturentAdress, int resturentZip) {
        this.resturentName = resturentName;
        this.resturentAdress = resturentAdress;
        this.resturentID = resturentID;
        this.resturentZip = resturentZip;
    }

    public Resturent(int resturentID) {
        this.resturentID = resturentID;
    }

    public String getResturentName() {
        return resturentName;
    }

    public String getResturentAdress() {
        return resturentAdress;
    }

    public int getResturentID() {
        return resturentID;
    }

    public int getResturentZip() {
        return resturentZip;
    }

    @Override
    public String toString() {
        return resturentID + " | " + resturentName;
    }
}
