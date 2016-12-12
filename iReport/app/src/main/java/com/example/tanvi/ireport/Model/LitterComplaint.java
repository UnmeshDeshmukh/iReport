package com.example.tanvi.ireport.Model;

/**
 * Created by Unmesh on 12/1/2016.
 */

public class LitterComplaint {

    private String description;
    private String severityLevel;
    private String litterSize;
    private double lat,lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getLitterSize() {
        return litterSize;
    }

    public void setLitterSize(String litterSize) {
        this.litterSize = litterSize;
    }
}
