package com.example.tanvi.ireport.Model;

/**
 * Created by tanvi on 12/2/2016.
 */
public class Report_Item {

    String name;
    String status;
    public String dateTime;
    int imageURL;

    public Report_Item(String name, String status, String dateTime, int imageURL) {
        this.name = name;
        this.status = status;
        this.dateTime = dateTime;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
        this.imageURL = imageURL;
    }
}
