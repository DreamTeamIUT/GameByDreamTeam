package unice.etu.dreamteam.Entities.Weapons;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;
import unice.etu.dreamteam.Entities.Weapons.Graphics.Weapon;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapons extends EntitiesHolder<WeaponHolder> {
    private static Weapons weapons;

    private Weapons() {
        super();
    }

    {
        path = "weapons";
    }

    public static Weapons getInstance() {
        if(weapons == null)
            weapons = new Weapons();

        return weapons;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new WeaponHolder(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entity : jsonIterator) {
            if(entity.toString().endsWith(".json"))
                this.add(new WeaponHolder(loadDependencies(path, entity.toString())));
        }
    }
}
