package com.sousablde.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //starting with constants that we will have to use repeatedly
    //base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";

    public final static String API_KEY = "6b49e5c8d23f64805bba6f86bb2fe4cd";

    //tag for logging from this activity
    public final static String TAG = "MovieListActivity";


    //instance fields
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize client on onCreate
        client = new AsyncHttpClient();
    }

    //get the configuration from the api
    private void getConfiguration() {
        //create url
        String url = API_BASE_URL + "/configuration";

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, API_KEY);

        //call the get request through get method expect JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
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
