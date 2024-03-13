package org.apollo.template.Domain;

public class BinStatus {

    int binStatusID, binID, weight;
    String dateTime;

    public BinStatus(int binStatusID, String dateTime, int weight, int binID){

        this.binStatusID = binStatusID;
        this.dateTime = dateTime;
        this.weight = weight;
        this.binID = binID;

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBinStatusID() {
        return binStatusID;
    }

    public void setBinStatusID(int binStatusID) {
        this.binStatusID = binStatusID;
    }

    public int getBinID() {
        return binID;
    }

    public void setBinID(int binID) {
        this.binID = binID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
