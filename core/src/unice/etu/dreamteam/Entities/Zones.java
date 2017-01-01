package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zones extends EntitiesHolder<Zone> {

    public Zones() {
        super();
    }

    public Zones(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new Zone(value));
    }

    public Boolean exist(String name) {
        return this.get(name) != null || name == null;
    }
}