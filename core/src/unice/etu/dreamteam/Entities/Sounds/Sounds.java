package unice.etu.dreamteam.Entities.Sounds;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

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

    @Override
    public Boolean add(JsonValue value) {
        return add(new Sound(value));
    }

}