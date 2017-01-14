package unice.etu.dreamteam.Entities.Weapons;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Weapons.Forces.ForceWeaponHolder;
import unice.etu.dreamteam.Entities.Weapons.Forces.ForcesWeaponHolder;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapon extends Entity {
    private ForcesWeaponHolder forces;

    public Weapon(JsonValue value) {
        super(value);

        forces = new ForcesWeaponHolder(value.get("forces").iterator());
    }

    public Graphic create(int powerful) {
        if(forces.areEnough()) {
            if(powerful < 0)
                powerful = forces.getDefaultPowerful();

            ForceWeaponHolder forceWeaponHolder = forces.get(powerful);

            Graphic graphic = new Graphic();

            return graphic;
        }

        return null;
    }

    public Graphic create() {
        return create(-1);
    }

    public ForceWeaponHolder getForceWeaponHolders(int powerful) {
        return forces.existPowerful(powerful) ? forces.get(powerful) : null;
    }

    public class Graphic {
        public Graphic() {
        }
    }
}
