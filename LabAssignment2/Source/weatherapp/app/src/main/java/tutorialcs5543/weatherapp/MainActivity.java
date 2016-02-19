package tutorialcs5543.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import tutorialcs5543.weatherapp.data.Channel;
import tutorialcs5543.weatherapp.data.Item;
import tutorialcs5543.weatherapp.service.WeatherServiceCallback;
import tutorialcs5543.weatherapp.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView WeatherIconImageView;
    private TextView TemperatureTextView;
    private TextView ConditionTextView;
    private TextView LocationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherIconImageView = (ImageView) findViewById(R.id.WeatherIconImageView);
        TemperatureTextView = (TextView) findViewById(R.id.TemperatureTextView);
        ConditionTextView = (TextView) findViewById(R.id.ConditionTextView);
        LocationTextView = (TextView) findViewById(R.id.LocationTextView);
        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service .refreshWeather("Austin,TX");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        int resourceId=getResources().getIdentifier("drawable/icon_" + channel.getItem().getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation") Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        WeatherIconImageView.setImageDrawable(weatherIconDrawable);
        TemperatureTextView.setText(channel.getItem().getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        ConditionTextView.setText(channel.getItem().getCondition().getDescription());
        LocationTextView.setText(service.getLocation());

    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }
}
