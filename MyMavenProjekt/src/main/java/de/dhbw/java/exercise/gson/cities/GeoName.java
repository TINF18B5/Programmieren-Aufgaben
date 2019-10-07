package de.dhbw.java.exercise.gson.cities;

import com.google.gson.annotations.*;

public class GeoName {
    private double lng, lat;
    private long geonameId, population;
    @SerializedName("countrycode")
    private String countryCode;
    private String name;
    private String fclName;
    private String toponymName;
    private String fcodeName;
    private String wikipedia;
    private String fcl;
    private String fcode;
    
    public double getLng() {
        return lng;
    }
    
    public void setLng(double lng) {
        this.lng = lng;
    }
    
    public double getLat() {
        return lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }
    
    public long getGeonameId() {
        return geonameId;
    }
    
    public void setGeonameId(long geonameId) {
        this.geonameId = geonameId;
    }
    
    public long getPopulation() {
        return population;
    }
    
    public void setPopulation(long population) {
        this.population = population;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFclName() {
        return fclName;
    }
    
    public void setFclName(String fclName) {
        this.fclName = fclName;
    }
    
    public String getToponymName() {
        return toponymName;
    }
    
    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }
    
    public String getFcodeName() {
        return fcodeName;
    }
    
    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }
    
    public String getWikipedia() {
        return wikipedia;
    }
    
    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }
    
    public String getFcl() {
        return fcl;
    }
    
    public void setFcl(String fcl) {
        this.fcl = fcl;
    }
    
    public String getFcode() {
        return fcode;
    }
    
    public void setFcode(String fcode) {
        this.fcode = fcode;
    }
}
