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

    //declare an Integer id field and parse its value from the JSONObject passed to the constructor using the key: id
    public int id;
    //adding a new field to track the vote_average value returned from the API
    Double voteAverage;

    // no-arg, empty constructor required for Parceler
    public Movie() {
    }

    //initiate from JSON
    public Movie(JSONObject object) throws JSONException {

        title = object.getString("title");

        overview = object.getString("overview");

        posterPath = object.getString("poster_path");

        backdropPath = object.getString("backdrop_path");

        voteAverage = object.getDouble("vote_average");

        id = object.getInt("id");

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

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }
}
