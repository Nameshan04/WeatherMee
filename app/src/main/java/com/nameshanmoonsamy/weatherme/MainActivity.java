package com.nameshanmoonsamy.weatherme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AccuWeatherURL";       //added this variable = added static on 22/08 @15:31pm
    //Fragment FiveDayWeather;
    //Fragment tideFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL accuWeatherURL = NetworkUtil.buildURL();
        Log.i(TAG,  "onCreate: " + accuWeatherURL);


        //FiveDayWeather = new FiveDayWeather();  //changed for singleton
        Fragment today = FiveDayWeather.getInstance();
        //tideFragment = new TideFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.weather_frame, today);
        //transaction.replace(R.id.tide_weather_frame, tideFragment);
        transaction.commit();
    }
}