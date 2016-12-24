package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Sounds extends EntitiesHolder<Sound> {
    public Sounds() {
        super();
    }

    public Sounds(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }
}