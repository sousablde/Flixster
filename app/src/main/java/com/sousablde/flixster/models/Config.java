package com.sousablde.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    //track the image values of the posters for loading the images in the right ratio
    String imageBaseUrl;

    //poster size to actually fetch the images
    String posterSize;

    //adding the fields that will help with the backdrop size for portrait mode
    //the backdrop size to use to fetch images
    String backdropSize;


    public Config(JSONObject object) throws JSONException {
        //adding correction to parse the images object before poster and logos
        JSONObject images = object.getJSONObject("images");

        //getting the values out of the json
        imageBaseUrl = images.getString("secure_base_url");

        //get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");

        //use the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");

        //parse the backdrop sizes and use the option for index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }

    //helper method to construct url
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path); //concatenate all three

    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
