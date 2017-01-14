package unice.etu.dreamteam.Entities.Weapons.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.ForceEntitiesHolder;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForcesWeaponHolder extends ForceEntitiesHolder<ForceWeaponHolder> {
    public ForcesWeaponHolder() {
        super();
    }

    public ForcesWeaponHolder(JsonValue.JsonIterator jsonIterator) {
        super();

        add(jsonIterator);
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new ForceWeaponHolder(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue value : jsonIterator)
            add(new ForceWeaponHolder(value));
    }
}
