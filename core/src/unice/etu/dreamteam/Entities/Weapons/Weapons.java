package unice.etu.dreamteam.Entities.Weapons;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapons extends EntitiesHolder<WeaponHolder> {
    public Weapons() {
        super();
    }

    public Weapons(JsonValue.JsonIterator jsonIterator) {
        super();
        add(jsonIterator, "weapons");
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new WeaponHolder(value));
    }
}
