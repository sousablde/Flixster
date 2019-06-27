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
    public Movie(JSONObject object) throws JSONException {

        title = object.getString("title");

        overview = object.getString("overview");

        posterPath = object.getString("poster_path");

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
