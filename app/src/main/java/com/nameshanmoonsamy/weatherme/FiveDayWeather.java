package com.nameshanmoonsamy.weatherme;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FiveDayWeather extends Fragment {  //a fragment lives in an activity

    //declaring variables for fragment view
    private TextView testTextView;
    ListView listView;
    CardView cardView;
    static FiveDayWeather instance; //added on 22/08    --for singleton method
    String TAG = "url";             //added on 22/08

    private FiveDayWeather() {  //made private for singleton - video L3B = change constructor in mainactivity for 5dayweather
        // Required empty public constructor
    }
    //added on 22/08
    public static FiveDayWeather getInstance() {
            if (instance == null) {
                return new FiveDayWeather();
            }
            return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        /*// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five_day_weather, container, false);*/
        View view = inflater.inflate(R.layout.fragment_five_day_weather, container, false);
        //return view;

//        testTextView = view.findViewById(R.id.weather_text);
//        testTextView.setText("WEATHER");
        //listView = view.findViewById(R.id.weather_list);
        cardView = view.findViewById(R.id.weather_cardview);
        //URL url = NetworkUtil.BuildURL();
        URL url = NetworkUtil.buildURL(); //call ur url here = still the same as the networkkutil class
        Log.d(TAG, "onCreateView: " + url);     //added on 22/08
        new FetchWeatherData().execute(url);    //a way of calling a class
        return view;
    }

    public class FetchWeatherData extends AsyncTask<URL,Void, String> {    //parameter = URL = uses our URL / VOID = if we want a progress update or we can put a progress bar / STRING = we know that we are getting string data back from our networkutil class
        //async class was depricated but it no longer is because its not showing any deprication if we use it in the IDE
        private String TAG ="weatherDATA";
        //make an arraylist
        ArrayList<Forecast> fiveDayList = new ArrayList<Forecast>();
        //once we implement the async class = it auto gives us the doInBackground method
        //manually coded up the doInBackground class from yt video = https://www.youtube.com/watch?v=d4Xp1nvVV3Q&list=PL480DYS-b_ke-DfaZGvAwt310qfnHXit_&index=6
        @Override
        protected String doInBackground(URL... urls){   //... = spread operator / quartz /varquartz = means arrays but any data type, any data structure

            URL weatherURL = urls[0];
            String weatherData = null;

            try
            {
                weatherData = NetworkUtil.getResponse(weatherURL);
                Log.d(TAG, "doInBackground: " + weatherData);   //added on 22/08
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.i(TAG, "ourDATA" + weatherData);

            return weatherData;
        }

        @Override
        protected void onPostExecute(String weatherData){
            if (weatherData != null)    //on 22/08 = weatherData == null
            {
                consumeJson(weatherData);
            }
            super.onPostExecute(weatherData);
        }

        public ArrayList<Forecast> consumeJson(String weatherJSON)
        {
            if (fiveDayList != null) //check if the arraylist is clear = if not clear = it will just add and add to it --->
            {
                fiveDayList.clear(); //if it is not clear = then clear it
            }
            if (weatherJSON != null)    //if the weatherjson is not 0 = then chop up that json manually
            {
                try {
                    JSONObject rootWeatherData = new JSONObject(weatherJSON);
                    JSONArray fivedayForecast = rootWeatherData.getJSONArray("DailyForecasts");

                    for (int i =0; i< fivedayForecast.length();i++)
                    {
                        Forecast forcastObject = new Forecast();

                        JSONObject dailyWeather = fivedayForecast.getJSONObject(i);

                        //get date
                        String date = dailyWeather.getString("Date");
                        Log.i(TAG, "consumeJson: Date" + date);
                        forcastObject.setDate(date);

                        //added on 22/08
                        //String date = dailyWeather.getString("date");
                        //newForecast.setDate(date);
                        //Log.i(TAG, "Daily date is : " + date);

                        //get Min
                        JSONObject temperatureObject = dailyWeather.getJSONObject("Temperature");
                        JSONObject minTempObject = temperatureObject.getJSONObject("Minimum");
                        String minTemp = minTempObject.getString("Value");
                        Log.i(TAG, "consumeJson: minTemp"+ minTemp);
                        forcastObject.setMinTemp(minTemp);

                        //get Max
                        JSONObject maxTempObject = temperatureObject.getJSONObject("Maximum");
                        String maxTemp = maxTempObject.getString("Value");
                        Log.i(TAG, "consumeJson: maxTemp"+ maxTemp);
                        forcastObject.setMaxTemp(maxTemp);

                        fiveDayList.add(forcastObject);

                        if (fiveDayList != null)
                        {
                            ForecastAdapter adapter = new ForecastAdapter(getContext(),fiveDayList);
                            listView.setAdapter(adapter); //red lined adapter = so alt enter = added listAdapter = WRONG = added = extends ArrayAdapter<Forecast>
                        }
                        //added on 22/08
                        /*JSONObject day = dailyWeather.getJSONObject("Day");
                        String iconValue = day.getString("IconPhrase");
                        forecastObject.setURL(iconValue);
                        Log.i(TAG, "iconValue" + iconValue);
                        fiveDayList.add(forecastObject);*/
                    }
                    Log.d(TAG, "consumedJSON: " + rootWeatherData); //added on 22/08
                    return fiveDayList;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}