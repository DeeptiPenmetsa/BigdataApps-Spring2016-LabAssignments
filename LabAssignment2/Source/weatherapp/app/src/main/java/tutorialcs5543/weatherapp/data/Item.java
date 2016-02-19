package tutorialcs5543.weatherapp.data;

import org.json.JSONObject;


/**
 * Created by DEEPU on 1/31/2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {

        condition= new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
