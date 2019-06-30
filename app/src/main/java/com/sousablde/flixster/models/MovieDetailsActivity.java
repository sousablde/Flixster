package com.sousablde.flixster.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sousablde.flixster.MovieTrailerActivity;
import com.sousablde.flixster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static com.sousablde.flixster.MovieListActivity.API_BASE_URL;
import static com.sousablde.flixster.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = MovieDetailsActivity.class.getSimpleName();

    //declare new field for the movie to display
    Movie movie;

    //Declare TextView fields for tvTitle and tvOverview and voting average
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    Button btnShowTrailer;

    //instance fields
    AsyncHttpClient client;



    //retrieve unwrap and assign field from onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //initialize client on onCreate
        client = new AsyncHttpClient();

        // resolve the view objects
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        btnShowTrailer = findViewById(R.id.btnShowTrailer);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        // create button on-click listener
        btnShowTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovieTrailer();
            }
        });



        // see MovieTrailerActivity


    }

    //create the network call to access the movies
    private void getMovieTrailer() {
        //create url by concatening fields
        String url = API_BASE_URL + "/movie/" + movie.id + "/videos" ;

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        //call the get request through get method expect JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load the result into movies list
                try {
                    JSONArray results = response.getJSONArray("results");
                    // parse response and extract "key" for YouTube trailer
                    String videoId = results.getJSONObject(0).getString("key");
                    // create an Intent to start MovieTrailerActivity
                    // create intent for the new activity
                    Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    intent.putExtra("key", videoId);
                    // pass "key" for youtube trailer as a parameter into Intent
                    startActivity(intent);
                    //add log statement that states successful completion
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));

                } catch (JSONException e) {
                    logError("Failed to parse now playing", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing", throwable, true);
            }
        });

    }

    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        //always log the error
        Log.e(TAG, message, error);
        //alert for silent errors
        if (alertUser) {
            //show long toast with error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        }
    }
}
