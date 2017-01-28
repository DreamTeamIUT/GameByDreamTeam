package unice.etu.dreamteam.Entities.Zones;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zones extends EntitiesHolder<Zone> {
    private static Zones zones;

    public Zones() {
        super();
    }

    public static Zones getInstance() {
        if(zones == null)
            zones = new Zones();

        return zones;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new Zone(value));
    }
}