package com.fiftyonred.wandersafe;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import java.util.*;
import java.io.IOException;

public class LocationProvider {
    private LocationManager locationManager;
    private final static String BASE_URL = "http://www.wandersafe.com";

    //TODO - Configure the radius / alert threshold
    private final static double RADIUS = 400.0;

    public LocationProvider(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Double[] getLocation() {

        List<String> providers = this.locationManager.getProviders(true);
        Location l = null;
        for (int i=providers.size()-1; i>=0; i--) {
            l = this.locationManager.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        Double[] gps = new Double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;

        //Location location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Double latitude  = 39.963155;
//        Double longitude = -75.146548;
//
//        if (location != null) {
//            latitude  = location.getLatitude();
//            longitude = location.getLongitude();
//        }
//        Double[] coords = new Double[]{latitude, longitude};
//        return coords;
    }

    public String getMapUrl() {
        Double[] coords = getLocation();
        if (coords[0] == null || coords[1] == null) {
            return BASE_URL;
        }
        return BASE_URL + "/map/" + coords[0] + "/" + coords[1];
    }

    /**
     * Gets the alert level based on the geo location and the radius
     * @param latitude
     * @param longitude
     * @return
     */
    public int getAlertLevel(double latitude, double longitude) {
        int level = -1;

        try {
            level = Utils.getJSONFromURL(BASE_URL + "/level/" + latitude + "/" + longitude + "/" + RADIUS);
        } catch (IOException e) {
            // TODO: catch exception
        }

        return level;
    }
}
