package org.apollo.template.Model;

public class Records {

    int recordID;
    String dateTime;
    int weight;

    public Records() {}

    public Records(int recordID, String dateTime, int weight) {
        this.recordID = recordID;
        this.dateTime = dateTime;
        this.weight = weight;
    }


    public String toString(){
        return String.format("%d, %s, %d", getRecordID(),getDateTime(),getWeight());
    }

    // region Getter and Setter
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // endregion
}
