package com.nameshanmoonsamy.weatherme;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static final String BASE_URL="https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    private static final String API_KEY = "VPNIFnAZj8d8KKUy0uD9XSOeGwJZ0CLB";
    private static final String PARAM_API_KEY = "apikey";
    private static final String METRIC_PARAM = "metric";
    private static final String METRIC_VALUE = "true";
    private static String TAG = "URLWECREATED";

    public static URL buildURL(){

        //build uri with uri builder
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(METRIC_PARAM, METRIC_VALUE)
                .build();

                URL url = null;


        try {
            url = new URL((uri).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "buildURLForWeather: " +url);
        return url;
    }

    public static String getResponse(URL url) throws IOException {

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //pull in existing java class
        try {
            //gets input from the Http GET request response
            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            //delimiter is used to finds the next line JSON
            scanner.useDelimiter("//A");//delimiter for JSON
            //checks if there is extra lines
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                //if there is return the next line
                return scanner.next();
            } else {
                //If there isnt extra lines return null
                Log.i(TAG, "getResponse: "+scanner.next());
                return null;
            }

        } finally {

            httpURLConnection.disconnect();
        }

    }

}
