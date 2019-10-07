package de.dhbw.java.exercise.gson.cities;

import com.google.gson.*;
import com.google.gson.annotations.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class GeoNameResult {
    
    public static void main(String[] args) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try(Reader r = new InputStreamReader(new URL("http://api.geonames.org/citiesJSON?formatted=true&north=49.5&south=48.5&east=8.8&west=8.2&lang=de&username=demo&style=full").openStream())) {
            System.out.println(gson.fromJson(r, GeoNameResult.class).getGeoNames().size());
        } catch(IOException e) {
            e.printStackTrace();
        }
    
    }
    
    @SerializedName("geonames")
    private List<GeoName> geoNames = new ArrayList<>();
    
    public List<GeoName> getGeoNames() {
        return geoNames;
    }
    
    public void setGeoNames(List<GeoName> geoNames) {
        this.geoNames = geoNames;
    }
}
