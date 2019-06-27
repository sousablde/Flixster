package com.sousablde.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

//this class tracks the info associated with the movie we want to display
public class Movie {

    //instance fields that track values coming from the api
    private String title;
    private String overview;
    private String posterPath;//not the full url

    //initiate from JSON
    public Movie(JSONObject object) {
        try {
            title = object.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            overview = object.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            posterPath = object.getString("poster_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
