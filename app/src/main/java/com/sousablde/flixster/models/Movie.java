package com.sousablde.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

//integrate parceler
@Parcel
//this class tracks the info associated with the movie we want to display
public class Movie {

    //instance fields that track values coming from the api
    public String title;
    public String overview;
    public String posterPath;//not the full url

    //adding the fields for the backdrop
    public String backdropPath;

    //initiate from JSON
    public Movie(JSONObject object) throws JSONException {

        title = object.getString("title");

        overview = object.getString("overview");

        posterPath = object.getString("poster_path");

        backdropPath = object.getString("backdrop_path");

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

    public String getBackdropPath() {
        return backdropPath;
    }
}
