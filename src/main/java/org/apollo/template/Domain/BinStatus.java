package org.apollo.template.Domain;

public class BinStatus {

    int binStatusID, binID, weight;
    String dateTime;
    String assignedResturent;

    public BinStatus(int binStatusID, String dateTime, int weight, int binID){

        this.binStatusID = binStatusID;
        this.dateTime = dateTime;
        this.weight = weight;
        this.binID = binID;

    }

    public BinStatus(String dateTime, int weight, int binID){

        this.binStatusID = binStatusID;
        this.dateTime = dateTime;
        this.weight = weight;
        this.binID = binID;
    }

    public BinStatus(String dateTime, int weight, int binID, String restaurant){

        this.binStatusID = binStatusID;
        this.dateTime = dateTime;
        this.weight = weight;
        this.binID = binID;
        this.assignedResturent = restaurant;
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

    public String getAssignedResturent() {
        return assignedResturent;
    }

    public void setAssignedResturent(String assignedResturent) {
        this.assignedResturent = assignedResturent;
    }

    @Override
    public String toString() {
        return "BinStatus{" +
                "binStatusID=" + binStatusID +
                ", binID=" + binID +
                ", weight=" + weight +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
