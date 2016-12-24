package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zones extends EntitiesHolder<Zone> {

    private ArrayList<Zone> zones;

    public Zones() {
        super();
    }

    public Zones(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);

    }

}