package tutorialcs5543.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by DEEPU on 1/31/2016.
 */
public class Units implements JSONPopulator{
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {

        temperature= data.optString("temperature");
    }
}
