package org.apollo.template.Domain;

import java.util.List;

public class Resturent {

    // Restaurant object
    private String resturentName, resturentAdress;
    private int resturentID, resturentZip;
    private List<Bin> binList;

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

    public List<Bin> getBinList() {
        return binList;
    }
}
