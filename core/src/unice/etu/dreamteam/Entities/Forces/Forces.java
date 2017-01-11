package unice.etu.dreamteam.Entities.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Forces extends EntitiesHolder<Force> {
    public Forces() {
        super();
    }

    public Forces(JsonValue.JsonIterator jsonIterator) {
        super();
        add(jsonIterator);
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new Force(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue jsonValue : jsonIterator)
            add(new Force(jsonValue));
    }
}
