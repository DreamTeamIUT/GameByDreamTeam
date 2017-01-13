package unice.etu.dreamteam.Entities.Gates;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

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

    @Override
    public Boolean add(JsonValue value) {
        return add(new Gate(value));
    }
}