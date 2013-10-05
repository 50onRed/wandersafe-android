package com.fiftyonred.wandersafe;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

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
        Location location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Double latitude  = 39.965906;
        Double longitude = -75.142692;

        if (location != null) {
            latitude  = location.getLatitude();
            longitude = location.getLongitude();
        }
        Double[] coords = new Double[]{latitude, longitude};
        return coords;
    }

    public String getMapUrl() {
        Double [] coords = getLocation();
        return BASE_URL + "/map/" + coords[0] + "/" + coords[1] + "/" + RADIUS;
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
