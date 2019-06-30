package com.sousablde.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sousablde.flixster.models.Config;
import com.sousablde.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //starting with constants that we will have to use repeatedly
    //base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";

    //tag for logging from this activity
    public final static String TAG = "MovieListActivity";


    //instance fields
    AsyncHttpClient client;


    //track parsed movies that come back/currently playing
    ArrayList<Movie> movies;

    //track the adapter using the recycler view
    //butterknife combines declaration and init
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    //the adapter wired to the recycler view
    MovieAdapter adapter;
    //image config
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        //initialize client on onCreate
        client = new AsyncHttpClient();

        //initialize the list of movies
        movies = new ArrayList<>();

        //initialize the adapter
        adapter = new MovieAdapter(movies);

        //resolve the reference to the recycler view and connect to layout manager
//        rvMovies = findViewById(R.id.rvMovies);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);


        //get the configuration on app creation
        getConfiguration();


    }

    //create the network call to access the movies
    private void getNowPLaying() {
        //create url
        String url = API_BASE_URL + "/movie/now_playing";

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
                    //iterate through array and pass objects to constructor adding movie object to list
                    for (int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));

                        //add that movie to the list
                        movies.add(movie);

                        //notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
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

    //get the configuration from the api
    private void getConfiguration() {
        //create url
        String url = API_BASE_URL + "/configuration";

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        //call the get request through get method expect JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.i(TAG, String.format("Loaded configuration with imageBasedURL %s and posterSize %s", config.getImageBaseUrl(), config.getPosterSize()));

                    //pass config to adapter
                    adapter.setConfig(config);
                    //get the now playing movie list
                    getNowPLaying();

                } catch (JSONException e) {
                    logError("Failed while parsing the configuration", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true
                );
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
