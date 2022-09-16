package com.nameshanmoonsamy.weatherme;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    public ForecastAdapter(@NonNull Context context, ArrayList<Forecast> forecastList) {
        super(context, 0,forecastList);
    }
}
