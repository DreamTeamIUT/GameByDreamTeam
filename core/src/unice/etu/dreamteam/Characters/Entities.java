package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Entities {

    protected ArrayList<JsonValue> entities;

    public Entities() {
        entities = new ArrayList<JsonValue>();
    }

    public Entities(JsonValue.JsonIterator jsonValues) {
        entities = new ArrayList<JsonValue>();
        add(jsonValues);
    }

    public void add(JsonValue value) {
        entities.add(value);
    }

    public JsonValue get(String name) {
        for (JsonValue value : entities) {
            if (value.name().equals(name))
                return value;
        }
        return null;
    }

    public JsonValue get(int i) {
        return entities.get(i);
    }

    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            entities.add(entitie);
    }

    public ArrayList<JsonValue> all() {
        return this.entities;
    }

}
