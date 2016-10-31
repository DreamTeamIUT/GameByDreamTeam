package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Entities;

/**
 * Created by Guillaume on 31/10/2016.
 */

public class Mobs extends Entities {
    public Mobs() {
        super();
    }

    public Mobs(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }
}