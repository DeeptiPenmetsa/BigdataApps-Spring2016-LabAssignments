package tutorialcs5543.weatherapp.service;

import tutorialcs5543.weatherapp.data.Channel;

/**
 * Created by DEEPU on 1/31/2016.
 */
public interface WeatherServiceCallback {
    void  serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);

}
