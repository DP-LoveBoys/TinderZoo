package com.dploveboys.TinderZoo.service;


import com.dploveboys.TinderZoo.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

//https://github.com/googlemaps/google-maps-services-java/blob/main/README.md
public class Geocoder{

    private static Geocoder geocoder_instance = null;

    private Geocoder()
    {

    }

    public static Geocoder getInstance()
    {
        if (geocoder_instance == null)
            geocoder_instance = new Geocoder();

        return geocoder_instance;
    }

    private static final String API_KEY="AIzaSyDga_7-7HVr7Yk0j5E7Mtl2xwEBYgoeOGs";
    OkHttpClient client = new OkHttpClient();

    public Location GeocodeSync(String address) throws IOException, InterruptedException, ApiException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        GeocodingResult[] results =  GeocodingApi.geocode(context, address
                ).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        Double lat = Double.valueOf(gson.toJson(results[0].geometry.location.lat));
        Double lng = Double.valueOf(gson.toJson(results[0].geometry.location.lng));

        Location location = new Location(lat, lng);

        return location;
    }

    public String[] calculate(String source , String destination) throws IOException, IllegalStateException {
        String url="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination+"&key="+ API_KEY;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonString = gson.toJson(response.body().string());
        String[] substrings = jsonString.split("\\[");
        String whatWeWant = substrings[substrings.length - 1];
        substrings = whatWeWant.split("\\\\");

        String distance = substrings[8];
        String time = substrings[20];

        System.out.println("distance: " + distance.substring(1));
        System.out.println("time: " + time.substring(1));

        String[] time_and_distance = {distance.substring(1), time.substring(1)};

        return time_and_distance;
    }

    public static void main(String[] args) throws InterruptedException, ApiException, IOException, IllegalStateException {
        Geocoder geo = new Geocoder();
        geo.GeocodeSync("74 Calea Sagului, Timisoara, Romania, 300515");

        System.out.println(geo.calculate("Timsoara Calea Sagului 74", "Timisoara Vasile Parvan"));
    }

}

