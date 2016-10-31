package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Entities;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zones extends Entities {
    public Zones() {
        super();
    }

    public Zones(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }
}