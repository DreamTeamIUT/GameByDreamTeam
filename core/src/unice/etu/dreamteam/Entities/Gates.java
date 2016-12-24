package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Gates extends EntitiesHolder<Gate> {
    public Gates() {
        super();
    }

    public Gates(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }
}